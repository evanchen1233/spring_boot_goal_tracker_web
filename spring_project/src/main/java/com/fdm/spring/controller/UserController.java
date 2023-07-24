package com.fdm.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.fdm.spring.model.User;
import com.fdm.spring.service.UserService;

@Controller
public class UserController
{
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserService userService;
	
    
	@GetMapping("/listUsers")
	public String listUsers(Model model) {
		
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		
		return ("/listUsers");
	}
	
	@GetMapping("/useredit")
	public String editUser(@RequestParam String username, Model model) {
		
		model.addAttribute("user", userService.getUserByUsername(username));
		model.addAttribute("message", "/listUsers");
		
		return("/useredit");
	}
	
	@PostMapping("/editUser")
	public String editUser
	(
		@RequestParam String username, @RequestParam String firstname, 
		@RequestParam String lastname, @RequestParam String password, Model model 
	)
	{
		String returnValue;
		
		// Check username unique
//		if((user = userService.getUserByUsername(username)) != null) {
//			model.addAttribute("message", "username already exists");
//			returnValue = "registeruser";
//		}
//		else {
			userService.saveUser(new User(username, firstname, lastname, password));
			returnValue = "traineeindex";
//		}
		
		return(returnValue);
	}	
	

	

	

	

}
