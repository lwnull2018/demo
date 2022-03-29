package com.jack.chen.session.share;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Description 请描述类的业务用途
 * @ClassName HelloController
 * @Author abc@123.com
 * @Date 2021/9/18 3:03 PM
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("/set")
    public String set(HttpSession session) {
        session.setAttribute("user", "jack");
        return String.valueOf(port);
    }

    @GetMapping("/get")
    public String get(HttpSession session) {
        return session.getAttribute("user") + ":" + port;
    }

}
