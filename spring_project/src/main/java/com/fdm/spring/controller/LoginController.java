package com.fdm.spring.controller;

import java.io.IOException;

import java.security.Principal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fdm.spring.service.RoleService;
import com.fdm.spring.service.StreamService;
import com.fdm.spring.service.UserService;
import com.fdm.spring.model.User;

/**
 * This is the controller class for login and password update operation
 * @author Yifeng Chen
 *
 */
@Controller
public class LoginController implements ErrorController{

	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * Default role id, only trainee can register an account through web
	 */
	private int DEFAULT_ROLE = 1;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	StreamService streamService;
	
	/**
	 * set default page as login page
	 * @return login.html
	 */
	@GetMapping("/")
	public String defaultpage() {
		
		return ("login"); 
	}
	
	/**
	 * Map the HTTP GET request of "/login" to login.html
	 * @return login.html
	 */
	@GetMapping("/login")
	public String login() {
		
		return ("login"); 
	}
	
	/**
	 * This method redirects users after login. 
	 * Admin will be sent to listTrainees.html
	 * Trainee will be sent to listGoals.html
	 * @param request
	 * @param response
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	@GetMapping("/login_success")
	public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        
        String role =  authResult.getAuthorities().toString();
        
        if(role.contains("ROLE_ADMIN")){
        	response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admin/listTrainees"));      
        	logger.info("ADMIN logged in");
         }
        else if(role.contains("ROLE_USER")) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/user/listGoals"));
            logger.info("Trainee logged in");
         }
    }
	
	/**
	 * Replace white label error page
	 * @return error.html
	 */
	@GetMapping("/error")
	public String getError() {

		return ("error");		
	}
    
	/**
	 * Map the HTTP GET request of "/register" to register_form.html
	 * Send user to registration page
	 * @param model
	 * @return register_form.html
	 */
	@GetMapping("/register")
	public String registerUser(Model model)
	{
		List<String> options = streamService.getStreamNames();
		
		model.addAttribute("user", new User());
		model.addAttribute("options", options);
		
		return ("register_form");
	}
		
	/**
	 * Map the HTTP POST request of "/addUserObject"
	 * This method tries to add an new user object. 
	 * Send user to login page if register successfully .
	 * Otherwise ask user to retry registration.
	 * @param option List of stream names populated from data source
	 * @param user
	 * @param model
	 * @return login.html if register successfully or register_form.html to try again
	 */
	@PostMapping("/addUserObject")
	public String addUserObject
	(
		@RequestParam String option, @ModelAttribute User user, Model model
	)
	{		
		// get stream names to populate SELECT input in html
		List<String> options = streamService.getStreamNames();
		model.addAttribute("options", options);	
		String msg = isPasswordValid(user.getPassword());
		
		// validate username and password
		if((userService.getUserByUsername(user.getUsername())) != null) {
			
			model.addAttribute("message", "Username already exists!");
			return("register_form");
		}
		else if (!isUsernameValid(user.getUsername())) {
			
			model.addAttribute("message", "Use your FDM Email as username!");
			return("register_form");
		}
		else if (!msg.equals("1")) {
			
			model.addAttribute("message", msg);
			return("register_form");
		}
		else {
			// encrypt password before saving
		    userService.changePassword(user, user.getPassword());
			user.setRole(roleService.getRoleById(DEFAULT_ROLE));
			user.setStream(streamService.getStreamByStreamName(option));
			userService.saveUser(user);
			
	        model.addAttribute("message", "You have signed up successfully. "
	                + "Please login.");
	        
	        logger.info("New trainee registered");
			return("login");
		}

	}	
	
	/**
	 * Map the HTTP GET request of "/changePassword/{id}"
	 * Display changePassword_form.html
	 * @param model
	 * @return changePassword_form.html
	 */
	@GetMapping("/changePassword")
	public String editUser(Model model){			
        
		return("changePassword_form");
	}
	
	/**
	 *  Map the HTTP POST request of "/updatePassword/{id}"
	 *  This method update a user's password. 
	 *  If password policy is not met, send user back to try again
	 * @param model
	 * @param principal currently authenticated user
	 * @param id path variable refer to user's username
	 * @param oldPassword
	 * @param newPassword
	 * @param request
	 * @param ra RedirectAttributes helps to add message after log out
	 * @return login.html if successful changed password, otherwise retry
	 * @throws ServletException
	 */
    @PostMapping("/updatePassword")
    public String updateUserObject
    (
    	 Model model, Principal principal,  @RequestParam String oldPassword, 
    	 @RequestParam String newPassword, HttpServletRequest request, 
    	 RedirectAttributes ra
    ) throws ServletException 
    {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    
    	User user = userService.getUserByUsername(principal.getName());

    	// if old password is same as new password
    	if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Your new password must be different than the old one.");
             
            return "changePassword_form";
    	}
    	
    	// check if encrypted old password matches
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("message", "Your old password is incorrect."); 
            
            return "changePassword_form";
        }
        
        // validate new password format
        String msg = isPasswordValid(newPassword);
        
		if (!msg.equals("1")) {
			
			model.addAttribute("message", msg);
			return("changePassword_form");
		}		

		//use changePassword method to encrypt and save new password
        userService.changePassword(user, newPassword);
        userService.updateUser(user);
        
        //logout user after changing password, send back to login page with message
        request.logout();
        ra.addFlashAttribute("message", "You have changed your password successfully. "
                + "Please login again.");
         
        logger.info("user password changed");
        return "redirect:/"; 

    }	
	
    /**
     * This method check if username has a  fdm email format
     * @param username
     * @return Boolean
     */
	public boolean isUsernameValid(String username) {
		
	    if (!username.matches("^[A-Za-z0-9+_.-]+@fdmgroup.com"))
	        return false;
	    else 
	    	return true;
	}
	
	/**
	 * This method validates a password and return corresponding error message.
	 * @param password
	 * @return Error messages or "1" for valid password
	 */
	public String isPasswordValid(String password) {
		
	    if (!password.matches(".*[A-Z].*")) {
	        return "Password should contain at least one uppercase letter!";
	    }
	    else if (!password.matches(".*[a-z].*")) {
	    	return "Password should contain at least one lowercase letter!";
	    }
	    else if (!password.matches(".*[0-9].*")) {
	    	return "Password should contain at least one number!";
	    }
	    else if (!password.matches(".*\\W.*")) {
	    	return "Password should contain at least one special character!";
	    }
	    else if (password.contains(" ")) {
	    	return "Password should not contain whitespace!";
	    }
	    else if (password.length() < 8 || password.length() > 16) {
	    	return "Password length should be between 8 and 16";
	    }
	    else 
	    	return "1";
	}
	
}
