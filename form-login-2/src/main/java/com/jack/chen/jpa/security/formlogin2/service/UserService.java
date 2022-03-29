package com.jack.chen.jpa.security.formlogin2.service;

import com.jack.chen.jpa.security.formlogin2.bean.User;
import com.jack.chen.jpa.security.formlogin2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 请描述类的业务用途
 * @ClassName UserService
 * @Author abc@123.com
 * @Date 2021/9/5 9:22 PM
 * @Version 1.0
 **/
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Autowired
    SessionRegistry sessionRegisty;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //用户已经登录则此次登录失败
        List<Object> allPrincipals = sessionRegisty.getAllPrincipals();
        for (Object principal : allPrincipals) {
            if (principal instanceof User && (user.getUsername().equals(((User) principal).getUsername()))) {
                throw new SessionAuthenticationException("当前用户已经在线，登录失败！");
            }
        }
        return user;
    }

}
