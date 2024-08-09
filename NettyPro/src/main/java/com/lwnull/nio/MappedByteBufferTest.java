package com.lwnull.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以直接让文件在内存中修改，操作系统无需拷贝
 * 注意：写完要用另外的软件打开，才能看到变化
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        //获取 FileChannel
        FileChannel fileChannel = randomAccessFile.getChannel();

        /*
         * 参数1： FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3： 5 ： 是映射到内存的大小(不是索引位置)，即将1.txt的多少个字符映射到内存
         * 可以修改的范围是 [0,5)
         * 实际类型是 DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'S');
        mappedByteBuffer.put(3, (byte) '8');
        mappedByteBuffer.put(5, (byte) 'T');//java.lang.IndexOutOfBoundsException 下标越界

        //关闭读写
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
