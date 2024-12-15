package com.wade.nettydemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Slf4j
public class NettyServer {

    EventLoopGroup bossGroup = new NioEventLoopGroup(2);
    EventLoopGroup workGroup = new NioEventLoopGroup(2);

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    public void start(Integer port) {
        log.info("NettyServer start !!!");
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(this.serverChannelInitializer)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("server start listen port: {}", port);
            future.channel().closeFuture().sync();
            if (future.isSuccess()) {
                log.info("run Netty server");
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    @PreDestroy
    public void destory() {
        this.bossGroup.shutdownGracefully();
        this.workGroup.shutdownGracefully();
        log.info("shut down Netty");
    }
}
