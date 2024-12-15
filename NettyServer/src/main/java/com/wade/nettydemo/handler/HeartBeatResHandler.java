package com.wade.nettydemo.handler;

import com.wade.nettydemo.enums.MessageType;
import com.wade.nettydemo.enums.NettyConstants;
import com.wade.nettydemo.model.NettyMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
public class HeartBeatResHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("HeartBeatResHandler.channelRead: [{}]", msg);

        String msgData = (String) msg;

        if (NettyConstants.userIdChannelMap.size() == 0) {
            if (msg.toString().contains(MessageType.REQHEARTBEAT.getMsgType())) {
                log.info("get heart beat request from client, do ack");
                String ackMsg = msgData.replace(NettyConstants.REQ_HEART_BEAT, NettyConstants.ACK_HEART_BEAT);
                NettyMsg nettyMsg = new NettyMsg(MessageType.ACKHEARTBEAT.getCode(),
                        NettyMsg.getCurrentTimeStamp(),
                        ackMsg);
                nettyMsg.getMsgHead().setMsgType((byte) MessageType.ACKHEARTBEAT.getCode());

                String ackHeartBeatMsg = NettyMsg.buildNettyMsg(nettyMsg);
                log.info("ack heart beat msg: [{}]", ackHeartBeatMsg);
                ctx.writeAndFlush(nettyMsg);
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
        NettyConstants.userIdChannelMap.clear();
        log.info("Server shut down");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("client read timeout!!");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("client write timeout!!");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("client all operation timeout!!");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
