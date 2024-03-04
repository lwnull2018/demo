package com.jack.chen.transactiondemo;

import com.jack.chen.transactiondemo.service.DeptService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class TransactionDemoApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(TransactionDemoApplication.class, args);
        Object bean = applicationContext.getBean(DeptService.class);
        System.out.println("启动完成..." + bean);
    }

}
