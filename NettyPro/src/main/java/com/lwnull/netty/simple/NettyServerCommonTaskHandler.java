package com.lwnull.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * 自定义一个Handler，需要继承 Netty 规定好的某个HandlerAdapter(规范)
 * 普通任务示例
 */
public class NettyServerCommonTaskHandler extends ChannelInboundHandlerAdapter {

    //读取事件，可以读取客户端发送过来的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //假如这里有一个比较耗时的业务 -> 异步执行 -> 提交该channel对应的NIOEventLoop 的 taskQueue中

        //解决方案1：用户程序自定义的普通任务，该任务会提交到taskQueue队列中
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 task 1", CharsetUtil.UTF_8));
                    System.out.println("channel hashcode=" + ctx.channel().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 task 2", CharsetUtil.UTF_8));
                    System.out.println("channel hashcode=" + ctx.channel().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //解决方案2：用户自定义定时任务，该任务是提交到ScheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端 schedule task 3", CharsetUtil.UTF_8));
                    System.out.println("channel hashcode=" + ctx.channel().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("任务添加完毕");

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
