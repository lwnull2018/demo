package com.jack.chen.springbootmybatis.service.impl;

import com.jack.chen.springbootmybatis.entity.Dept;
import com.jack.chen.springbootmybatis.mapper.DeptMapper;
import com.jack.chen.springbootmybatis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> selectAll() {
        return deptMapper.selectAll();
    }

    @Override
    public List<Dept> queryAll() {
        return deptMapper.queryAll();
    }

    @Override
    public int insert(Dept dept) {
        return deptMapper.insert(dept);
    }

}
