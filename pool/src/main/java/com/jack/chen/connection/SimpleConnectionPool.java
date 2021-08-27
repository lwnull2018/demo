package com.jack.chen.connection;

import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 1. 初始化一个数据库连接池，并向里面添加10个数据库连接；
 * 2. 从连接池中获取连接；
 * 3. 当程序用完连接后，需要将该连接重新放入连接池中。
 */
public class SimpleConnectionPool {

    //创建一个存放连接的池子，注意要保证线程安全
    //因为要频繁的对数据库连接池取出和存放操作，所以使用LinkedList池子
    public static LinkedList<Connection> pool = new LinkedList<Connection>(Collections.synchronizedList(new LinkedList<Connection>()));

    //在类加载后向数据库连接池中存放10个数据库连接
    static {
        try {
            for (int i=0; i<10; i++) {
                System.out.println("往pool中加入连接对象 i="+i);
                pool.add(DBUtil.getConnection());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        if (pool.size() > 0) {
            return pool.removeFirst();
        } else {
            //说明没有可用的连接
            throw new RuntimeException("没有可用的连接");
        }
    }

    public static void release(Connection connection) {
        System.out.println("回收连接对象");
        pool.add(connection);
    }

}
