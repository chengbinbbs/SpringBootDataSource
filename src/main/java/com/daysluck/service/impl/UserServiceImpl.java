package com.daysluck.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daysluck.domain.User;
import com.daysluck.mapper.UserMapper;
import com.daysluck.service.UserService;
import com.github.pagehelper.PageHelper;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	private static final Integer pagesize = 3;
	
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	public int insertByUser(User user) {
		return userMapper.insertByUser(user);
	}

	/**
	 * 利用PageHelper分页插件分页查询
	 * @param pageno:第几页
	 * @param pagesize:每页显示数目
	 */
	public List<User> findByAge(Integer age,Integer pageno) {
		PageHelper.startPage(pageno,pagesize);
		return userMapper.findByAge(age);
	}

}
