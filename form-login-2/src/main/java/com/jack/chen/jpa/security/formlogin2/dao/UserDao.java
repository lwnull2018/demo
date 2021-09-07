package com.jack.chen.jpa.security.formlogin2.dao;

import com.jack.chen.jpa.security.formlogin2.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 请描述类的业务用途
 * @ClassName UserDao
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/5 9:19 PM
 * @Version 1.0
 **/
public interface UserDao extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
