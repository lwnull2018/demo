package com.jack.chen.transactiondemo.service;

import com.jack.chen.transactiondemo.entity.Dept;

import java.util.List;

public interface DeptService {

    public int insert(Dept dept);

    public int testA(Dept dept);

    public int testB(Dept dept);

    List<Dept> selectAll();
}
