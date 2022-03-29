package com.jack.chen.vuedemo1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Description 数据获取示例类
 * @ClassName DataDemoController
 * @Author abc@123.com
 * @Date 2021/9/18 3:03 PM
 * @Version 1.0
 **/
@RestController
public class DataDemoController {

    @Value("${server.port}")
    Integer port;

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/fetch")
    @ResponseBody
    public ResponseEntity<Object> fetch(HttpSession session) {
        return new ResponseEntity<Object>("获取成功", HttpStatus.OK);
    }


}
