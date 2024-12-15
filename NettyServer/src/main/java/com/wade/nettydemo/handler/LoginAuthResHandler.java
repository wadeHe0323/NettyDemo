package com.wade.nettydemo.handler;

import com.wade.nettydemo.enums.MessageType;
import com.wade.nettydemo.enums.NettyConstants;
import com.wade.nettydemo.model.NettyMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ChannelHandler.Sharable
@Component
public class LoginAuthResHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("LoginAuthResHandler.channelRead: [{}]", msg);

        String msgData = (String) msg;

        if (NettyConstants.userIdChannelMap.size() == 0) {
            if (msg.toString().contains(MessageType.REQLOGINALARM.getMsgType())) {
                String ackLoginAlarmMsg;
                if (msgData.contains(NettyConstants.ALARM_LOGIN_REQ_SUCCESS)) {
                    NettyConstants.userIdChannelMap.put(msgData, ctx.channel());
                    ackLoginAlarmMsg = NettyConstants.ACK_LOGIN_ALARM_RESULT_OK;
                } else {
                    ackLoginAlarmMsg = NettyConstants.ACK_LOGIN_ALARM_RESULT_FAIL;
                }
                NettyMsg nettyMsg = new NettyMsg(MessageType.ACKLOGINALARM.getCode(),
                        NettyMsg.getCurrentTimeStamp(),
                        ackLoginAlarmMsg);
                nettyMsg.getMsgHead().setMsgType((byte) MessageType.ACKLOGINALARM.getCode());
                log.info("login msg: [{}]", nettyMsg);
                ctx.writeAndFlush(nettyMsg);
            } else {
                log.info("user not login request: [{}], do nothing.", msgData);
            }
        } else {
            log.info("user login, get msg: [{}]", msgData);
            ctx.fireChannelRead(msgData);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
