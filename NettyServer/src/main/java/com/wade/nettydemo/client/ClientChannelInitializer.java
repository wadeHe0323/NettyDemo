package com.wade.nettydemo.client;

import com.wade.nettydemo.parser.MsgPackDecoder;
import com.wade.nettydemo.parser.MsgPackEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(30, 30, 60, TimeUnit.SECONDS));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 100, 0, 2, 0, 2));
        pipeline.addLast(new MsgPackDecoder());
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast(new MsgPackEncoder());
        pipeline.addLast(new HeartBeatReqHandler());
        pipeline.addLast(new LoginAuthReqHandler());
        pipeline.addLast(new ChatReqHandler());

    }
}
