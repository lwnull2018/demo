package com.jack.chen.jpa.security.formlogin2.provider;

import com.jack.chen.jpa.security.formlogin2.config.MyWebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description 验证码的验证逻辑
 * @ClassName MyAuthenticationProvider
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/7 1:23 PM
 * @Version 1.0
 **/
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (!((MyWebAuthenticationDetails) authentication.getDetails()).isPassed()) {
            throw new AuthenticationServiceException("验证码错误");
        }
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
