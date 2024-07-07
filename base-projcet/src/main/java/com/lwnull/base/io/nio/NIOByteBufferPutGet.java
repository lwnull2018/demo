package com.lwnull.base.io.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        //创建一个缓冲区 Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        //类型化放入数据
        byteBuffer.putInt(10);
        byteBuffer.putDouble(8.88);
        byteBuffer.putLong(1001L);
        byteBuffer.putChar('好');
        byteBuffer.putShort((short) 6);

        //buffer转换
        byteBuffer.flip();

        //取出对应数据
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }

}
