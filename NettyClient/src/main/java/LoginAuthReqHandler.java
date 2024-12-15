import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyMsg nettyMsg = new NettyMsg(null,
                null,
                NettyConstants.ALARM_LOGIN_REQ_SUCCESS);
        nettyMsg.getMsgHead().setMsgType((byte) MessageType.REQLOGINALARM.getCode());

        System.out.println("LoginAuthReqHandler.channelActive: Send login msg: " + nettyMsg);
        ctx.writeAndFlush(nettyMsg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("LoginAuthReqHandler.channelRead: get msg: " + msg);
        String msgData = msg.toString();
        if (msgData.contains(MessageType.ACKLOGINALARM.getMsgType())) {
            if (msgData.contains(NettyConstants.ACK_LOGIN_ALARM_RESULT_OK)) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println("login success");
                ctx.pipeline().remove(this);
                ctx.fireChannelActive();
            } else {
                System.out.println("login failed");
                ctx.channel().close();
            }
        } else {
            ctx.fireChannelActive();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("login validation done, not need valid again");
        } else {
            System.out.println("not have login validation, close connect");
        }
    }
}
