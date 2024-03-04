# 1. 项目说明
该项目是SpringBoot整合Mybatis-plus示例

|组件| 版本    |
|:---|:------|
|JDK | 17    |
|springboot   | 3.0.3 |
|mybatis-spring-boot-starter| 3.0.3 |
 |mybatis-plus-boot-starter| 3.5.5 |

# 2. 测试
```java
@SpringBootTest
class SpringbootMybatisPlusApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<User> userList = userMapper.selectList(null);
		Assert.isTrue(5 == userList.size(), "");
		userList.forEach(System.out::println);
	}

}
```
输出结果：
>User(id=1, name=Jone, age=18, email=test1@baomidou.com)
User(id=2, name=Jack, age=20, email=test2@baomidou.com)
User(id=3, name=Tom, age=28, email=test3@baomidou.com)
User(id=4, name=Sandy, age=21, email=test4@baomidou.com)
User(id=5, name=Billie, age=24, email=test5@baomidou.com)

# 3. 小结
通过以上几个简单的步骤，我们就实现了 User 表的 CRUD 功能，甚至连 XML 文件都不用编写！

从以上步骤中，我们可以看到集成MyBatis-Plus非常的简单，只需要引入 starter 工程，并配置 mapper 扫描路径即可。

>注意
> 
>引入 MyBatis-Plus 之后请不要再次引入 MyBatis 以及 mybatis-spring-boot-starter和MyBatis-Spring，以避免因版本差异导致的问题。

#

[Mybatis-Plus官网]

```mysql
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    id BIGINT NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);


INSERT INTO `user` (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');

```

[Mybatis-Plus官网]: https://baomidou.com/pages/bab2db/#release