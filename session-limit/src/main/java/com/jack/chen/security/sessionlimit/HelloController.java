package com.jack.chen.security.sessionlimit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 请描述类的业务用途
 * @ClassName HelloController
 * @Author abc@123.com
 * @Date 2021/9/8 10:38 AM
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
