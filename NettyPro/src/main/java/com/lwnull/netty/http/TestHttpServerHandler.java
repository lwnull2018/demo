package com.lwnull.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 说明：
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 * 2. HttpObject 客户端与服务器通讯的数据被封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     * @param ctx 上下文对象
     * @param msg 通讯的数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println("对应的channel=" + ctx.channel() + ", pipeline=" + ctx.pipeline()
        + ", 通过pipeline获取channel=" + ctx.pipeline().channel());

        //判断msg是不是HttpRequest请求
        if (msg instanceof HttpRequest) {

            System.out.println("ctx 类型=" + ctx.getClass());

            System.out.println("pipeline hashCode=" + ctx.pipeline().hashCode() + " , TestHttpServerHandler hashCode=" + this.hashCode());

            System.out.println("msg类型=" + msg.getClass());
            System.out.println("客户端地址=" + ctx.channel().remoteAddress());

            //获取URI，过滤指定资源
            HttpRequest httpRequest = (HttpRequest)msg;
            URI uri = new URI(httpRequest.uri());
            System.out.println("uri path = " + uri.getPath());

            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }

            //回复消息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("Hello，我是服务器", CharsetUtil.UTF_8);

            //构造一个http响应，即HttpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //构建好 response 返回
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

        }
    }

}
