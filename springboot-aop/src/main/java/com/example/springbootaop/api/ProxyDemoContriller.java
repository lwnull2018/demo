package com.example.springbootaop.api;

import com.example.springbootaop.aop.ApiPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProxyDemoContriller {

    @RequestMapping(value = "/update")
    @ApiPermission
    public void update() {
        log.info("执行修改操作");
    }

}
