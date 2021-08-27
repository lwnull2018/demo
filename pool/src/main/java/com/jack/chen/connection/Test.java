package com.jack.chen.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description 请描述类的业务用途
 * @ClassName Test
 * @Author lwnull2018@gmail.com
 * @Date 2021/8/6 2:00 PM
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) throws SQLException {
        String sql = "select * from agent";
        Connection connection = SimpleConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            System.out.println("username = "+username);
        }
        SimpleConnectionPool.release(connection);
    }

}
