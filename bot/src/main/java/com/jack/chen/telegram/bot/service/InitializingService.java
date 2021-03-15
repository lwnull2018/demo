package com.jack.chen.telegram.bot.service;

import com.jack.chen.telegram.bot.MyBot;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Created by lwnull2018@gmail.com ON 2020/10/20.
 */
@Component
public class InitializingService implements InitializingBean {


    @Autowired
    private CustomerActiveService customerActiveService;

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;


    static {
        ApiContextInitializer.init();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 实例化Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {

            // 注册我们的机器人
            botsApi.registerBot(new MyBot(username, token, customerActiveService));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



}
