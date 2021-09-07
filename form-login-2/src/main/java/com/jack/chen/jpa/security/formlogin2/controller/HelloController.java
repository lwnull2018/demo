package com.jack.chen.jpa.security.formlogin2.controller;

import com.jack.chen.jpa.security.formlogin2.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 请描述类的业务用途
 * @ClassName HelloController
 * @Author lwnull2018@gmail.com
 * @Date 2021/9/3 12:00 PM
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        helloService.hello();
        return "hello, Spring Security";
    }

    @RequestMapping("/s")
    public String success() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(" name = "+name);
        return "ssss";
    }

    @GetMapping("/admin/hello")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "user";
    }

    @GetMapping("/rememberme")
    public String rememberme() {
        return "rememberme";
    }

}
