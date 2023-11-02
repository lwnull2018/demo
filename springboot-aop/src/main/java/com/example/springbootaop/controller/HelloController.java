package com.example.springbootaop.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

    @RequestMapping(value = "/setHi", method = RequestMethod.GET)
    @ResponseBody
    public String setHello(@RequestParam String name) {
        return "setHello " + name;
    }

}
