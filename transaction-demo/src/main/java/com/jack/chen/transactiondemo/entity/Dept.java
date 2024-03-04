package com.jack.chen.transactiondemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {

    static final long serialVersionUID = 1L;

    private String deptno;

    private String dname;

    private String loc;

}
