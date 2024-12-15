package com.wade.nettydemo;

import com.wade.nettydemo.handler.ChatResHandler;
import com.wade.nettydemo.handler.HeartBeatResHandler;
import com.wade.nettydemo.handler.LoginAuthResHandler;
import com.wade.nettydemo.parser.MsgPackDecoder;
import com.wade.nettydemo.parser.MsgPackEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ChatResHandler chatResHandler;
    @Autowired
    private HeartBeatResHandler heartBeatResHandler;
    @Autowired
    private LoginAuthResHandler loginAuthResHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new IdleStateHandler(30, 60, 60, TimeUnit.SECONDS));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024 * 100, 0, 2, 0, 2));
        pipeline.addLast(new MsgPackDecoder());
        pipeline.addLast(new LengthFieldPrepender(2));
        pipeline.addLast(new MsgPackEncoder());

        pipeline.addLast(this.heartBeatResHandler);
        pipeline.addLast(this.loginAuthResHandler);
        pipeline.addLast(this.chatResHandler);

    }
}
