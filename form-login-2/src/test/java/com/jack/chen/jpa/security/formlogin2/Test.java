package com.jack.chen.jpa.security.formlogin2;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @Description 请描述类的业务用途
 * @ClassName Test
 * @Author abc@123.com
 * @Date 2021/9/6 2:16 PM
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args)  throws  UnsupportedEncodingException{
        String s =  new String(Base64.getDecoder().decode("RkVKQlZINDBFY1pjUHpNMzM1MyUyQnNRJTNEJTNEOnRkOFBBMVBnc3hIbFluSGlzTCUyQmZSZyUzRCUzRA"), "UTF-8");
        System.out.println("s = " + s);
    }

}
