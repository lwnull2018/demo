package com.jack.chen.transactiondemo.service.impl;

import com.jack.chen.transactiondemo.entity.Dept;
import com.jack.chen.transactiondemo.mapper.DeptMapper;
import com.jack.chen.transactiondemo.service.DeptService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Transactional
    @Override
    public int insert(Dept dept) {
        int r = deptMapper.insert(dept);
        System.out.println("====== inser r = " + r);
        if (dept.getDeptno().equals("okok")) {
            r = 10/0;
        }
        return r;
    }

    /**
     * 方法testA没有加事务，调用同一个类里的testB方法，testB方法有标注事务，testB中发生异常，看会不会回滚
     * @param dept
     * @return
     */
    @Override
    public int testA(Dept dept) {
        //为了解决方法B中发生异常不会回滚的问题，需要采用代理的方式调用testB方法
//        DeptService deptService = (DeptService)applicationContext.getBean(DeptService.class);
        //通过AopContext获取当前代理对象，通过代理对象调用方法B就会触发事务
//        DeptService deptService = DeptService.class.cast(AopContext.currentProxy());
        DeptService deptService = (DeptService)AopContext.currentProxy();
        System.out.println("testA deptService " + deptService);
        return deptService.testB(dept);
    }

    @Transactional
    @Override
    public int testB(Dept dept) {
        int r = deptMapper.insert(dept);
        System.out.println("====== testB r = " + r);
        if (dept.getDeptno().equals("okok")) {
            r = 10/0;
        }
        return r;
    }

    @Override
    public List<Dept> selectAll() {
        return deptMapper.selectAll();
    }
}
