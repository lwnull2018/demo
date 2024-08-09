package com.lwnull.nio;

import java.nio.IntBuffer;

/**
 * 简单的Buffer使用
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //获取一个容量为5的Int缓冲区，该缓冲区可以放置5个int类型的数据
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //添加数据到缓冲区
        for (int i=0; i<5; i++) {
            intBuffer.put(i * 2);
        }

        //如何从buffer读取数据
        //将 buffer 转换，读写切换(!!!)
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {//是否还有遗留数据
            System.out.println(intBuffer.get());
        }
    }

}
