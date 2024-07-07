package com.lwnull.base.io.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 应用 ByteBuffer 和 FileChannel 将字符串 "Hello,中国" 写入file01.txt 文件中
 */
public class NIOFileChannel {

    public static void main(String[] args) throws IOException {

        String str = "Hello,中国";

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("file01.txt");

        //通过输出流获取到 fileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区 Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将字符内容放入到缓冲区中
        byteBuffer.put(str.getBytes());

        //将 buffer 读写转换
        byteBuffer.flip();

        //将 byteBuffer 数据写入到 fileChannel
        int write = fileChannel.write(byteBuffer);
        System.out.println("write=" + write);

        fileOutputStream.close();

    }
}
