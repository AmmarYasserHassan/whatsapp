package controller.pipelines;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import invoker.ControllerInvoker;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import org.json.JSONObject;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class JSONHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        ByteBuf buffer = (ByteBuf) o;
        JSONObject jsonObject = new JSONObject(buffer.toString(CharsetUtil.UTF_8));
        String command = jsonObject.getString("command");
        JsonObject request = new JsonParser().parse(jsonObject.toString()).getAsJsonObject();
        String result = ControllerInvoker.getInstance().invoke(command, request);

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(result.getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

        response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        channelHandlerContext.write(response);
    }

}
