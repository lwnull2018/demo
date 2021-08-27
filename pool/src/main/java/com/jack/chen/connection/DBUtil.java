package com.jack.chen.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description 请描述类的业务用途
 * @ClassName DBUtil
 * @Author lwnull2018@gmail.com
 * @Date 2021/8/6 1:43 PM
 * @Version 1.0
 **/
public class DBUtil {

    public static final String driverClass = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://ls-eca05824a01496118938340037e220bd35a282f8.ckt7vxpwqwnx.ap-southeast-1.rds.amazonaws.com:3306/yizhifu?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&useSSL=false";
    public static final String username = "dbmasteruser";
    public static final String password = "e<__FA%0OUjg4B|^+5vl-*}(VW7FeYZJ";

    static {
        try {
            //加载驱动
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
