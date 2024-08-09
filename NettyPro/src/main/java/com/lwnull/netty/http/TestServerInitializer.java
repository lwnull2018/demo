package com.lwnull.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        //向管道加入处理器

        //得到管理
        ChannelPipeline pipeline = channel.pipeline();
        //加入一个netty提供的HttpServerCodec codec -> [coder - decoder]
        //1. HttpServerCodec 是netty提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //2. 增加一个自定义的handler
        pipeline.addLast("MyHttpServerHandler", new TestHttpServerHandler());

        System.out.println("ok~~~~");
    }

}
