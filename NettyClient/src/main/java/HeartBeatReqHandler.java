import com.wade.nettydemo.AlarmNettyClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatReqHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HeartBeatReqHandler.channelActive...");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HeartBeatReqHandler.channelRead: get msg: " + msg);

        String msgData = (String) msg;
        System.out.println("get msg transfer to string: " + msgData);

        if (LoginUtil.hasLogin(ctx.channel())) {
            if (msgData.contains(MessageType.ACKHEARTBEAT.getMsgType())) {
                System.out.println("get heartbeat response: " + msgData);
            } else {
                ctx.fireChannelRead(msgData);
            }
        } else {
            ctx.fireChannelRead(msgData);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        UserChannelUtil.unBindUser(ctx.channel());
        LoginUtil.logOut(ctx.channel());
        System.out.println("client closed.");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("client read timeout!!");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("client write timeout!!");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("client all operation timeout!!");
            }

            NettyMsg nettyMsg = new NettyMsg(null,
                    null,
                    getHeartBeatReqMsg());
            nettyMsg.getMsgHead().setMsgType((byte) MessageType.REQHEARTBEAT.getCode());

            System.out.println("send heartbeat request: " + nettyMsg);
            ctx.writeAndFlush(nettyMsg);
        }
    }

    private String getHeartBeatReqMsg() {
        int reqId = AlarmNettyClient.REQID.incrementAndGet();
        String reqHeartBeatMsg = Boolean.TRUE + NettyConstants.REQ_HEART_BEAT + "reqId=" + reqId;
        return reqHeartBeatMsg;
    }
}
