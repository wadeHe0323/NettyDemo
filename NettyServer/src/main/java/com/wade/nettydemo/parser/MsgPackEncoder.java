package com.wade.nettydemo.parser;

import com.wade.nettydemo.model.NettyMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MsgPackEncoder extends MessageToByteEncoder<NettyMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMsg nettyMsg, ByteBuf byteBuf) throws Exception {
        log.info("MsgPackEncoder.encode: {}", nettyMsg);
        byteBuf.writeShort(nettyMsg.getMsgHead().getStartSign());
        byteBuf.writeByte(nettyMsg.getMsgHead().getMsgType());
        byteBuf.writeInt(nettyMsg.getMsgHead().getTimeStamp());
        byte[] bytes = nettyMsg.getMsg().getBytes();
        byteBuf.writeShort((short) bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
