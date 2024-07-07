package com.lwnull.base.io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 群聊服务端
 */
public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    //构造器
    //初始化工作
    public GroupChatServer() {

        try {
            //得到Selector
            selector = Selector.open();

            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();

            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);

            //channel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {

        try {
            while (true) {
                int count = selector.select(2000);
                if (count == 0) {
                    System.out.println("等待...");
                    continue;
                }
                System.out.println("count=" + count);
                //有事件
                //拿到SelectionKey集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                //遍历
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    //监听到 accept
                    if (selectionKey.isAcceptable()) {
                        //生成一个 SocketChannel
                        SocketChannel socketChannel = listenChannel.accept();
                        socketChannel.configureBlocking(false);

                        //将socketChannel 注册到selector
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println(socketChannel.getRemoteAddress() + " 上线了");
                    }
                    //监听到 read
                    if (selectionKey.isReadable()) {
                        System.out.println("监听到读的事件...");
                        readData(selectionKey);
                    }

                    //移除当前的key，防止重复操作
                    iterator.remove();
                    System.out.println("移除当前key = " + selectionKey.hashCode());
                }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }

    }

    //读取客户端消息
    public void readData(SelectionKey key) {
        SocketChannel channel = null;

        try {
            channel = (SocketChannel)key.channel();

            //创建一个ByteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //将通道中的内容读取到缓冲区
            int read = channel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端：" + msg);

                //向其他客户端了送消息(排队自己)
                sendMsgToOthersClient(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线...");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //给其他客户端发送消息
    public void sendMsgToOthersClient(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        //获取所有的注册
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            SelectableChannel targetChannel = key.channel();

            if (targetChannel instanceof SocketChannel dest && targetChannel != self) {//排队自己
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                //写数据：将byteBuffer数据写入到通道
                dest.write(byteBuffer);
            }

        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
