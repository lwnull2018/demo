package com.lwnull.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {

        //建立连接
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666));

        String fileName = "PlistEditPro.zip";
        //得到一个文件channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long t1 = System.currentTimeMillis();
        //transferTo 底层使用零拷贝
        long transferToCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("总发送的字节数：" + transferToCount + " , 耗时：" + (System.currentTimeMillis() - t1));

        fileChannel.close();
    }
}
