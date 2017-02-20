package com.daysluck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daysluck.domain.EmailDTO;
import com.daysluck.domain.User;
import com.daysluck.service.EmailSendService;
import com.daysluck.service.UserService;

@RestController
public class UserController {

	
	@Autowired
	private UserService userService;
	@Autowired
	private EmailSendService emailSendService;
	
	
	@GetMapping("/user/{name}")
	public User findByName(@PathVariable("name") String name){
		return userService.findByName(name);
	}
	
	@PostMapping("/user/regist")
	public String register(@RequestParam("name") String name, @RequestParam("age") Integer age){
		int ret = userService.insertByUser(new User(name,age));
//		if(ret > 0){
//			emailSendService.sendSimpleEmail(new EmailDTO());
//		}
		return "success";
	}
	
	@GetMapping("/user/find")
	public List<User> findByAge(@RequestParam("age") Integer age,@RequestParam("pageno") Integer pageno){
		return userService.findByAge(age,pageno);
	}
	
}
