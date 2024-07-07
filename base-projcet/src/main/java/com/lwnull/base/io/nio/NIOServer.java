package com.lwnull.base.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个NIO 入门案例：实现服务器端和客户端的数据简单通讯
 *  1. 通过ServerSocketChannel 得到 SocketChannel，监听6666端口，并启动
 *  2. 将SocketChannel 注册到 Selector 上，一个Selector 可以注册多个SocketChannel
 *  3. 注册后会返回一个 SelectionKey，会和该Selector关联(集合)
 *  4. 通过 SelectionKey 可以反向得到一个 SocketChannel
 *  5. 得到channel 完成业务处理
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        //得到一个 Selector 对象
        Selector selector = Selector.open();

        //把 serverSocketChannel 注册到 selector，关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的 selectionKey 数量=" + selector.keys().size());

        //循环等待客户端连接
        while (true) {

            int select = selector.select(1000);
            System.out.println("select=" + select);
            if (select == 0) {
                System.out.println("服务端等待1秒，没有连接");
                continue;
            }

            //如果>0表示获取到关注的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys 数量 = "+selectionKeys.size());

            //通过SelectionKey 反向获取通道
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()) {
                //获取SelectionKey
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {//如果是 OP_ACCEPT 表示有新客户端连接
                    //为该客户端生成一个 socketChannel，每一个新的客户端生成一个新的 socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个 SocketChannel " + socketChannel.hashCode());

                    //将SocketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);

                    //将SocketChannel注册到 Selector ，关注事件为 OP_READ，并为其分配一个 ByteBuffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("客户端连接后，注册的 SelectionKey 的数量=" + selector.keys().size());

                }
                if (key.isReadable()) {//发生 OP_READ 事件

                    //通过 key 反向获取 channel
                    SocketChannel channel = (SocketChannel)key.channel();

                    //获取到该channel 关联的 ByteBuffer
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    channel.read(byteBuffer);

                    System.out.println("from 客户端 " + new String(byteBuffer.array()));
                }

                //手动从集合中移除当前selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }

}
