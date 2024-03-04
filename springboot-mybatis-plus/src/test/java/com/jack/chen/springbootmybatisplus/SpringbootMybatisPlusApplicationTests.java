package com.jack.chen.springbootmybatisplus;

import com.jack.chen.springbootmybatisplus.bean.User;
import com.jack.chen.springbootmybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

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

	@Test
	public void testInsert() {
		System.out.println(("----- insert method test ------"));

		User user = new User();
		user.setId(6L);
		user.setName("Jack");
		user.setEmail("test6@test.com");
		user.setAge(25);

		int r = userMapper.insert(user);
		Assert.isTrue(1 == r, "");
		System.out.println(r);
	}

	@Test
	public void testUpdate() {
		System.out.println(("----- update method test ------"));

		User user = new User();
		user.setId(6L);
		user.setName("Jack-chen");
		user.setEmail("test7@test.com");
		user.setAge(24);

		int r = userMapper.updateById(user);
		Assert.isTrue(1 == r, "");
		System.out.println(r);
	}

	@Test
	public void testDelete() {
		System.out.println(("----- delete method test ------"));

		int r = userMapper.deleteById(6);
		Assert.isTrue(1 == r, "");
		System.out.println(r);
	}

}
