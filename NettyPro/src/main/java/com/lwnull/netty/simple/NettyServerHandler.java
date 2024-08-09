package com.lwnull.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;


/**
 * 自定义一个Handler，需要继承 Netty 规定好的某个HandlerAdapter(规范)
 */
public class NettyServerHandler  extends ChannelInboundHandlerAdapter {

    //读取事件，可以读取客户端发送过来的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName() + " , channel = " + ctx.channel());
        System.out.println("server ctx : " + ctx);

        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();//本质是一个双向链表

        //将msg转成一个 ByteBuf
        //ByteBuf 是 Netty 提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("客户端了送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是：" + channel.remoteAddress());

    }

    //数据读取完毕事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是 write + flush
        //将数据写入缓存 并 刷新
        //一般会对要发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~~~", CharsetUtil.UTF_8));
    }

    //异常发生后，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
