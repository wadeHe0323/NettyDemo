package com.wade.nettydemo.model;

import lombok.Data;

@Data
public class NettyMsgHead {

    private short startSign = (short) 0xFFFF;
    private byte msgType;
    private int timeStamp;

    public NettyMsgHead() {
        this.timeStamp = (int) NettyMsg.getCurrentTimeStamp();
    }
}
