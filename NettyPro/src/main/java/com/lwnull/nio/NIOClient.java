package com.lwnull.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {

        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        //连接的 IP 和 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，但客户端不会阻塞，可以做其他事情");
            }
        }

        System.out.println("连接成功，可以开始发送数据...");

        String msg = "Hello，欧洲杯";

        //Wraps a byte array into a buffer.
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        //发送数据，将byteBuffer 数据写入 socketChannel
        socketChannel.write(byteBuffer);
//        System.in.read();
    }
}
