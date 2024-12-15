package com.wade.nettydemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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

    // TODO
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }

    public static String buildNettyMsg(NettyMsg nettyMsg) {
        return Boolean.TRUE.toString()
                + nettyMsg.getMsgTypeCode() == null ? "" : nettyMsg.getMsgTypeCode() + ","
                + nettyMsg.getTimeStamp() == null ? "" : nettyMsg.getTimeStamp() + ","
                + nettyMsg.getMsg();

    }
}
