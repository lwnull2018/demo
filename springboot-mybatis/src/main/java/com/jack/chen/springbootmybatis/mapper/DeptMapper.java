package com.jack.chen.springbootmybatis.mapper;

import com.jack.chen.springbootmybatis.entity.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> selectAll();

    List<Dept> queryAll();

    @Insert("insert into dept(deptno, dname, loc) values(#{deptno}, #{dname}, #{loc})")
    int insert(Dept dept);

}
