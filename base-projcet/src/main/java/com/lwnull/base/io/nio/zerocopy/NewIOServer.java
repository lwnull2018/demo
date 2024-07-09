package com.lwnull.base.io.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {

    public static void main(String[] args) throws IOException {

        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        //绑定端口
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        long count = 0L;
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            while (readCount != -1) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                    if (readCount > 0) count += readCount;
                } catch (IOException e) {
                    System.out.println("发生异常，异常消息：" + e.getMessage());
                    break;
                }
                byteBuffer.rewind();//倒带 position 置为0，mark 作废
                System.out.println("读取字节大小：" + count);
            }
        }
    }
}
