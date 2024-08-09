package com.lwnull.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读取项目根目录下的1.txt文件，应用FileChannel 和 ByteBuffer 将其内容拷贝到另一个 2.txt 文件中
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true) {//循环读取
            /**
             * public Buffer clear() {
             *     position = 0;
             *     limit = capacity;
             *     mark = -1;
             *     return this;
             *  }
             */
            //这个清空动作很重要，清空了才能接着读取
            byteBuffer.clear();

            //将源文件的数据内容读取到缓冲区
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read:" + read);

            if (read == -1) {//当为 -1 时读取结束
                break;
            }

            //将缓冲区内容写出到文件
            byteBuffer.flip();
            int write = fileChannel02.write(byteBuffer);
            System.out.println("write:" + write);
        }

        //关闭输入流、输出流
        fileInputStream.close();
        fileChannel02.close();

    }

}
