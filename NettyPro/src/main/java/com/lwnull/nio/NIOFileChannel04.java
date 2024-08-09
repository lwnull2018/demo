package com.lwnull.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("a2.jpg");

        //获取各个流对应的FileChannel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();

        //使用transferFrom完成文件拷贝
        long bytes = destChannel.transferFrom(sourceChannel, 0,sourceChannel.size());
        System.out.println("bytes:" + bytes);

        //关闭相关的资源
        sourceChannel.close();
        destChannel.close();
        fileOutputStream.close();
        fileInputStream.close();
    }

}
