package com.jack.chen.jpa.security.formlogin2.service;

import com.jack.chen.jpa.security.formlogin2.bean.User;
import com.jack.chen.jpa.security.formlogin2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description 请描述类的业务用途
 * @ClassName UserService
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/5 9:22 PM
 * @Version 1.0
 **/
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

}
