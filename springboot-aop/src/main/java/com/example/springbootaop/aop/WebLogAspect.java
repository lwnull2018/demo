package com.example.springbootaop.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@Aspect //将一个java类定义为切面类
@Component  //将该类将入Spring bean factory
@Slf4j
public class WebLogAspect {

    //解决线程访问共享变量的安全问题
    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    /**
     * 定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等
     */
    @Pointcut("execution(* com.example.springbootaop.controller.*.set*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("@Before 我是前置通知...");
        //记录进入方法时的时间
        startTime.set(System.currentTimeMillis());

        //接受请求，记录请求内容
        /*ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //切面逻辑：记录请求内容
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + getIpAddress(request));
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        log.info("Parameters                  : {}", geParametersString(request));
        log.info("RequestJson                 : {}", getSubscribeJson(request));
        //modifiers: public 1, private 2, protected 4, static 8, final 16,
        int modifiers = joinPoint.getSignature().getModifiers();
        String modifoersStr = Modifier.toString(modifiers);
        log.info(": "+joinPoint.getSignature().getName() + " - " + joinPoint.getSignature().getModifiers() + " - " + modifoersStr);*/
    }

    @After("webLog()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        log.info("@After 我是后置通知...");
    }

    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("@AfterReturning 我是返回后通知 Response: "+ ret);
        //记录方法的执行时间
        log.info("doAfterReturning Spend time :"+ (System.currentTimeMillis() -startTime.get()));
    }

    @Around("webLog()")
    public Object doArround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("我是环绕通知之前。。。");
        String proceed = (String)joinPoint.proceed();
        proceed += "增强返回值";
        log.info("我是环绕通知之后执行:"+proceed);
        return proceed;
    }

    /**
     * 获取表单提交的参数信息
     *
     * @param request requestp
     * @return parameterString
     */
    private String geParametersString(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder stringBuilder = new StringBuilder();
        parameterMap.forEach((k, v) -> stringBuilder.append(k).append("=").append(Arrays.toString(v)).append(" "));
        return stringBuilder.toString();
    }

    /**
     * 从request中获取json
     *
     * @param request request
     * @return parameterJson
     */
    public String getSubscribeJson(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (!"application/json".equals(contentType)) {
            return null;
        }
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error("从request中获取json数据失败");
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("getSubscribeJson()关闭数据流失败");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取客户端ip
     *
     * @param request request
     * @return ip
     */
    private String getIpAddress(HttpServletRequest request) {
        String unknown = "unknown";
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}