# 1. 项目说明
这是一个Springboot整合mybatis的环境，搭建了基础框架，方便进行一些验证性的试验操作

# 2. 组件版本
 | 组件              | 版本     |
|-----------------|--------|
 | springboot      | 3.2.3  |
| mysql-connector | 8.3.0  |
| mybatis         | 3.0.3  |
| mysql           | 8.0.28 |

# 3. 表结构
```mysql
create table dept(
	`deptno` VARCHAR(20) NOT NULL,
	`dname` VARCHAR(100) NULL DEFAULT NULL COMMENT '部门名称',
	`loc` VARCHAR(100) NULL DEFAULT NULL COMMENT '部门地址'
)
```

