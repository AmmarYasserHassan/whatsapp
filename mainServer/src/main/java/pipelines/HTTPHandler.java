package pipelines;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HTTPHandler extends SimpleChannelInboundHandler<Object> {
    private HttpRequest request;
    private String requestBody;
    private long correlationId;
    volatile String responseBody;
    ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            ctx.fireChannelRead(content.copy());
        }
        if (msg instanceof LastHttpContent) {
            HttpObject trailer = (HttpObject) msg;
        }

    }


    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                CONTINUE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

