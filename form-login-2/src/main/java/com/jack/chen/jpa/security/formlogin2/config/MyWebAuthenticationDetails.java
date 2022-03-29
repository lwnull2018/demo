package com.jack.chen.jpa.security.formlogin2.config;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 请描述类的业务用途
 * @ClassName MyWebAuthenticationDetails
 * @Author abc@123.com
 * @Date 2021/9/7 4:50 PM
 * @Version 1.0
 **/
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean isPassed = true;

    public MyWebAuthenticationDetails(HttpServletRequest req) {
        super(req);
        /*String code = req.getParameter("code");
        String verify_code = (String) req.getSession().getAttribute("verify_code");
        if (code != null && verify_code != null && code.equals(verify_code)) {
            isPassed = true;
        }*/
    }

    public boolean isPassed() {
        return isPassed;
    }

}
