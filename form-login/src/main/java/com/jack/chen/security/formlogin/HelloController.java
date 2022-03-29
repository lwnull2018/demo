package com.jack.chen.security.formlogin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 请描述类的业务用途
 * @ClassName HelloController
 * @Author abc@123.com
 * @Date 2021/9/3 12:00 PM
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
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

}
