package com.lwnull.base.io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 群聊客户端
 */
public class GroupChatClient {

    //定义属性
    private final String HOST = "127.0.0.1";//服务器IP
    private final int PORT = 6666;//端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造函数
    //初始化
    public GroupChatClient() {
        try {
            selector = Selector.open();
            //连接服务器
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);

            //注册到Selector
            socketChannel.register(selector, SelectionKey.OP_READ);

            String localAddress = socketChannel.getLocalAddress().toString();
            System.out.println("localAddress=" + localAddress);

            username = localAddress.substring(1);
            System.out.println(username + " is OK...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送消息
    public void sendMsg(String msg) {
        msg = username + " 说：" + msg;
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取消息
    public void readMsg() {
        System.out.println("读取服务端消息...");
        try {
            int select = selector.select();
            if (select > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        //将缓冲区数据转成字符串
                        String msg = new String(byteBuffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();//移除当前key，防止重复操作
            } else {
                System.out.println("没有可用的通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        GroupChatClient chatClient = new GroupChatClient();

        //启动一个线程，每隔3秒，从读取服务端返回的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    chatClient.readMsg();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("休息结束...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            System.out.println("发送内容：" + str);
            chatClient.sendMsg(str);
        }
    }
}
