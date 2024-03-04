package com.jack.chen.springbootmybatisplus.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("`user`")
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String email;

}
