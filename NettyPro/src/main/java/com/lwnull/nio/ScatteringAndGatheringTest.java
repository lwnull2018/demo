package com.lwnull.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering：发散、分散：将数据写入 buffer 时，可以采用 buffer 数组依次写入。【分散】
 * Gathering： 聚集：从 buffer 读取数据时，可以采用 buffer 数组，依次读。【聚集】
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);

        //绑定端口到 socket 并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建 buffer 数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8;//假设从客户端接收8个字节

        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;//累计读取的字节数
                //使用流打印，查看当前这个buffer 的 position 和 limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position() + " , limit=" + buffer.limit());

                //将所有 buffer 进行 flip
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

                //将数据读出写回到客户端(telnet)
                int byteWrite = 0;
                while(byteWrite < messageLength) {
                    long write = socketChannel.write(byteBuffers);
                    byteWrite += write;
                }

                //将所有的 buffer 进行 clear
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());

                System.out.println("byteRead=" + byteRead + " , byteWrite=" + byteWrite + ",messageLength=" + messageLength);
            }
            System.out.println("byteRead=" + byteRead);
        }
    }
}
