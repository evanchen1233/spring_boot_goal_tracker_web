package com.fdm.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.spring.controller.LoginController;
import com.fdm.spring.model.*;
import com.fdm.spring.service.UserService;

@SpringBootTest
class ControllerTest {

	MockMvc mockMvc;	
	LoginController logincontroller;
	UserService userService;
	
    @BeforeEach
    void setUp(WebApplicationContext wac) {
    	logincontroller = new LoginController();
    	userService = new UserService();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test_default_page() throws Exception {
    	
        mockMvc.perform(get("/"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("login"));
    }
    
    @Test
    public void test_error_page() throws Exception {
    	
        mockMvc.perform(get("/error"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("error"));
    }
    
    @Test
    public void test_get_Login() throws Exception {
    	
        mockMvc.perform(get("/login"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("login"));
    }
    
    @Test
    public void test_get_registration() throws Exception {
    	
        mockMvc.perform(get("/register"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("register_form"));
    }
    
    @Test
    public void test_post_registration() throws Exception {
    	
        User user = new User("yifeng.chen@fdmgroup.com", "Yifeng", "Chen", "123");
        user.setPassword("123");
        
        // Convert the user object to JSON
        String userJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void test_get_change_password() throws Exception {
    	
        mockMvc.perform(get("/changePassword"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("changePassword_form"));
    }
    
    @Test
    public void test_username_format_valid() throws Exception {
    	
    	String username = "123@fdmgroup.com";
    	
    	Assertions.assertEquals(true, logincontroller.isUsernameValid(username));
    }
    
    @Test
    public void test_username_format_invalid() throws Exception {
    	
    	String username = "123";
    	
    	Assertions.assertEquals(false, logincontroller.isUsernameValid(username));
    }
    
    @Test
    public void test_password_format_valid() throws Exception {
    	
    	String password = "J@va1234";
    	
    	Assertions.assertEquals("1", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid() throws Exception {
    	
    	String password = "abcd";
    	
    	Assertions.assertEquals("Password should contain at least one uppercase letter!", logincontroller.isPasswordValid(password));
    }

    @Test
    public void test_password_format_invalid2() throws Exception {
    	
    	String password = "AAAA";
    	
    	Assertions.assertEquals("Password should contain at least one lowercase letter!", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid3() throws Exception {
    	
    	String password = "AAAaaa";
    	
    	Assertions.assertEquals("Password should contain at least one number!", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid4() throws Exception {
    	
    	String password = "AAAaaa11";
    	
    	Assertions.assertEquals("Password should contain at least one special character!", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid5() throws Exception {
    	
    	String password = "AAa  @11";
    	
    	Assertions.assertEquals("Password should not contain whitespace!", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid6() throws Exception {
    	
    	String password = "Aa1@";
    	
    	Assertions.assertEquals("Password length should be between 8 and 16", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_password_format_invalid7() throws Exception {
    	
    	String password = "Aa1@11111111111111111";
    	
    	Assertions.assertEquals("Password length should be between 8 and 16", logincontroller.isPasswordValid(password));
    }
    
    @Test
    public void test_get_admin_main_page() throws Exception {
    	  	
        mockMvc.perform(get("/admin/listTrainees"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("listTrainees"));
    }
    
    @Test
    public void test_get_admin_check_trainee_page() throws Exception {
    	  	
    	String id = "Yifeng";
    	
        mockMvc.perform(get("/admin/userDetail/{id}", id))
        .andExpect(status().isOk()) 
        .andExpect(view().name("userdetails"));
    }
        
    @Test
    public void test_get_add_goal_page() throws Exception {
    	  		
        mockMvc.perform(get("/user/addGoal"))
        .andExpect(status().isOk()) 
        .andExpect(view().name("addGoal_form"));
    }
    
}
