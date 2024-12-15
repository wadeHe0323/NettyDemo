package com.wade.nettydemo.parser;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MsgPackDecoder extends ByteToMessageDecoder {

    private short headData = (short) 0xFFFF;
    private int headLength = 9;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        log.info("MsgPackDecoder.decode");

        if (byteBuf.readableBytes() < headLength) {
            return;
        }
        byteBuf.markReaderIndex();
        short startIndex = byteBuf.readShort();
        log.info("startIndex:{}", startIndex);
        if(startIndex != headData) {
            byteBuf.resetReaderIndex();
            throw new CorruptedFrameException("invalid start index: " + startIndex);
        }
        byte msgType = byteBuf.readByte();
        log.info("msgType:{}", msgType);
        byteBuf.markReaderIndex();
        int timeStamp = byteBuf.readInt();
        log.info("timeStamp:{}", timeStamp);
        byteBuf.markReaderIndex();

        short lenOfBody = byteBuf.readShort();
        log.info("lenOfBody:{}", lenOfBody);
        if (byteBuf.readableBytes() < lenOfBody) {
            byteBuf.resetReaderIndex();
            return;
        }
        byteBuf.markReaderIndex();

        byte[] msgByte = new byte[lenOfBody];
        byteBuf.readBytes(msgByte);
        String msgContent = new String(msgByte);
        log.info("get byte transfor String: {}", msgContent);
        list.add(msgContent);
    }
}
