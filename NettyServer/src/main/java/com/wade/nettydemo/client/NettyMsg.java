package com.wade.nettydemo.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Data
public class NettyMsg {

    private Integer msgTypeCode;
    private Long timeStamp;
    private String msg;
    private NettyMsgHead msgHead;

    public NettyMsg(Integer msgTypeCode, Long timeStamp, String msg) {
        this.msgTypeCode = msgTypeCode;
        this.timeStamp = timeStamp;
        this.msg = msg;
        this.msgHead = new NettyMsgHead();
    }

    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static String buildNettyMsg(NettyMsg nettyMsg) {
        return Boolean.TRUE.toString()
                + (nettyMsg.getMsgTypeCode() == null ? "" : nettyMsg.getMsgTypeCode())
                + (nettyMsg.getTimeStamp() == null ? "" : nettyMsg.getTimeStamp())
                + nettyMsg.getMsg();
    }
}
