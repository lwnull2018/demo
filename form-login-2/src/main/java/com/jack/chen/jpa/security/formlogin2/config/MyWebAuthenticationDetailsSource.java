package com.jack.chen.jpa.security.formlogin2.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 请描述类的业务用途
 * @ClassName MyWebAuthenticationDetailsSource
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/7 4:50 PM
 * @Version 1.0
 **/
@Component
public class MyWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest,MyWebAuthenticationDetails>  {

    @Override
    public MyWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new MyWebAuthenticationDetails(request);
    }

}
