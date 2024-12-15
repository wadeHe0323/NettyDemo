import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ChatReqHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChatReqHandler.channelActive: already standby, please send msg:");
        new Thread(() -> {
            while (true) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                System.out.println("input msg: " + line);
                NettyMsg nettyMsg = new NettyMsg(MessageType.REQSYNCALARMMSG.getCode(),
                        NettyMsg.getCurrentTimeStamp(),
                        line
                );
                nettyMsg.getMsgHead().setMsgType((byte) MessageType.REQSYNCALARMMSG.getCode());
                System.out.println("send msg: " + nettyMsg);
                ctx.writeAndFlush(nettyMsg);
            }
        }).start();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ChatReqHandler.channelRead: get msg: "+  msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
