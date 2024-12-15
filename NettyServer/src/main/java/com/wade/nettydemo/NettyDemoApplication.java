package com.wade.nettydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    @Value("${netty.socket.port:10809}")
    private Integer serverPort;

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("===== do run netty Server =====");
        this.nettyServer.start(this.serverPort);
    }
}
