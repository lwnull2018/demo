package com.lwnull.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO示例
 * 启动一个客户端与之通讯：telent 127.0.0.1 666666
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {

        //思路
        //1.创建一个线程池
        //2.如果有客户端连接，则创建一个线程与之通讯
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务启动成功......");

        while (true) {
            System.out.println("线程ID:" + Thread.currentThread().getId() + " , 线程名称:" + Thread.currentThread().getName());
            System.out.println("等待连接......");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端......");
            //创建一个线程与之通信
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    //编写一个handler方法，用于和客户端通讯
    private static void handler(Socket socket) {
        try {
            System.out.println("线程ID:" + Thread.currentThread().getId() + " , 线程名称:" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程ID:" + Thread.currentThread().getId() + " , 线程名称:" + Thread.currentThread().getName());
                //阻塞：等待客户端输入
                System.out.println("等待客户端输入...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭和client的连接
            System.out.println("关闭和client的连接......");
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
