package com.example.springbootaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面类
 */
@Component
@Aspect
@Slf4j
public class AspectPermission {

    /**
     * 通过注解作为一个切入点
     */
    @Pointcut("@annotation(com.example.springbootaop.aop.ApiPermission)")
    public  void updateApi() {

    }

    @Around("updateApi()")
    public  Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("查询用户token");
        log.info("通过token解析出用户ID");
        log.info("查询数据库有没有此权限");
        if (validation()) {
            String proceed = (String)joinPoint.proceed();
            return proceed;
        } else {
            log.error("该用户没有权限直接返回");
            return null;
        }
    }

    private boolean validation() {
        return false;
    }

    @After("updateApi()")
    public void doAfter() {
        log.info("有权限，执行完毕。");
    }

}
