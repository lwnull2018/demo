package com.jack.chen.springbootmybatis.controller;

import com.jack.chen.springbootmybatis.entity.Dept;
import com.jack.chen.springbootmybatis.mapper.DeptMapper;
import com.jack.chen.springbootmybatis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class HelloController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/hello")
    public Object hell() {
        return "SpringBoot-MyBatis hello";
    }

    @GetMapping("add")
    @ResponseBody
    public Object add() {
        Dept dept = new Dept();

        int random = new Random().nextInt(100);
        dept.setDeptno(String.valueOf(random));
        dept.setDname("广告-" + random);
        dept.setLoc("address-" + random);

        int insert = deptService.insert(dept);
        return insert > 0 ? "插入成功 " + dept : "插入失败 " + dept;
    }

    @GetMapping("selectAll")
    @ResponseBody
    public Object all() {
        List<Dept> depts = deptService.selectAll();
        return depts;
    }

    @GetMapping("queryAll")
    @ResponseBody
    public Object queryAll() {
        List<Dept> depts = deptService.queryAll();
        return depts;
    }

}
