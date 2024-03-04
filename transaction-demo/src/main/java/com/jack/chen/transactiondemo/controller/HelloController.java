package com.jack.chen.transactiondemo.controller;

import com.jack.chen.transactiondemo.entity.Dept;
import com.jack.chen.transactiondemo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private DeptService deptSerivce;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    @ResponseBody
    public Object hi() {
        return "hello transactional ";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public Object insert() {
        Dept dept = new Dept();
        dept.setDeptno("okok");
        dept.setDname("jsjsjs");
        dept.setLoc("dddd");
        int r = deptSerivce.insert(dept);

        return r > 0 ? "插入成功" : "插入失败";
    }

    /**
     * 测试A方法没加事务，再调用加了事务的B方法，在B方法中发生异常，看是否会回滚
     * 结论：事务不会回滚，数据会正常插入
     * @return
     */
    @RequestMapping(value = "/testA", method = RequestMethod.GET)
    @ResponseBody
    public Object insertTestA() {
        Dept dept = new Dept();
        dept.setDeptno("okok");
        dept.setDname("jsjsjs");
        dept.setLoc("dddd");
        int r = deptSerivce.testA(dept);

        return r > 0 ? "插入成功" : "插入失败";
    }



    @GetMapping("selectAll")
    public Object selectAll() {
        return deptSerivce.selectAll();
    }

}
