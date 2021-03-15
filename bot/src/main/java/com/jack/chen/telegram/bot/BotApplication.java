package com.jack.chen.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@RestController
public class BotApplication {

	public static void main(String[] args) {

        // 初始化Api上下文
        ApiContextInitializer.init();

		SpringApplication.run(BotApplication.class, args);

	}

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {


        return String.format("Hello %s!", name);
    }

}
