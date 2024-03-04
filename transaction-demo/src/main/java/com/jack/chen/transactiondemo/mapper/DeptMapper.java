package com.jack.chen.transactiondemo.mapper;

import com.jack.chen.transactiondemo.entity.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Insert("insert into dept(deptno, dname, loc) values(#{deptno}, #{dname}, #{loc})")
    int insert(Dept dept);

    @Select("select * from dept")
    List<Dept> selectAll();
}
