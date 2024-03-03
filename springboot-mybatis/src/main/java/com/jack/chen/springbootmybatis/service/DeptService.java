package com.jack.chen.springbootmybatis.service;

import com.jack.chen.springbootmybatis.entity.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> selectAll();

    List<Dept> queryAll();

    int insert(Dept dept);

}
