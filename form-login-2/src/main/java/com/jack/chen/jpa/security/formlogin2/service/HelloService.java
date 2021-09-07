package com.jack.chen.jpa.security.formlogin2.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

/**
 * @Description 请描述类的业务用途
 * @ClassName HelloService
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/7 4:37 PM
 * @Version 1.0
 **/
@Service
public class HelloService {

    public void hello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println(details);
    }

}
