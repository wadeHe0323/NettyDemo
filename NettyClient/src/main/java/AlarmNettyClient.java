import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AlarmNettyClient {

    public static AtomicInteger REQID = new AtomicInteger(1);

    EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host) throws InterruptedException {
        ChannelFuture future = null;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ClientChannelInitializer());

            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("connect server failed!! msg: "+ e.getMessage());
        } finally {
            if (null != future) {
                if (future.channel() != null && future.channel().isOpen()) {
                    future.channel().close();
                }
            }
            System.out.println(System.currentTimeMillis() + " Prepare re-connect");
            Thread.sleep(3000);
            connect(port, host);
            System.out.println(System.currentTimeMillis() + " re-connect success");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new AlarmNettyClient().connect(NettyConstants.LOCAL_PORT, NettyConstants.LOCAL_IP);
    }
}
