package com.lwnull.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 应用ByteBuffer 和 FileChannel 从文件中读取数据，并输出到控制台
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws IOException {

        //获取文件
        //通过文件获取输入流
        File file = new File("file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //获取FileChannel
        FileChannel fileChannel = fileInputStream.getChannel();

        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将fileChannel中的数据读取到ByteBuffer 缓冲区中
        int read = fileChannel.read(byteBuffer);
        System.out.println("read:" + read);

        System.out.println(new String(byteBuffer.array()));

        //关闭输入流
        fileInputStream.close();

    }

}
