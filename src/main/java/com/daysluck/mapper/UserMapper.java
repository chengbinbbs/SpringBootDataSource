package com.daysluck.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daysluck.domain.User;

public interface UserMapper {

	public User findByName(@Param("name") String name);
	
	int insertByUser(User user);
	
	List<User> findByAge(Integer age);
}
