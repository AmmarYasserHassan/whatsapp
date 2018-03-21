import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import pipelines.HTTPServerInitializer;


public class HTTPServer {
    public static void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HTTPServerInitializer());
            b.option(ChannelOption.SO_KEEPALIVE, true);
            Channel ch = b.bind(port).sync().channel();

            System.err.println("Server is listening on http://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HTTPServer.start(8080);
    }
}
