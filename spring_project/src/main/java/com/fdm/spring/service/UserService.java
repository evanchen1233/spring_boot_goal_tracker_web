package com.fdm.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdm.spring.dal.UserRepository;
import com.fdm.spring.model.Goal;
import com.fdm.spring.model.User;

/**
 * This is a Spring service class for User. It performs CURD and other operations.
 * @author Yifeng Chen
 *
 */
@Service
public class UserService
{
	@Autowired
	UserRepository userRepository;		// create by default

	/**
	 * 
	 * @param username
	 * @return User object by given username
	 */
	public User getUserByUsername(String username) {
		
		Optional<User> user = userRepository.findById(username);
		
		if(user.isPresent())			// process optional
			return user.get();
		else 
			return null;
	}
	
	/**
	 * 
	 * @param name user's first name
	 * @return User object by given first name
	 */
	public User getUserByName(String name) {
		
		User user = userRepository.findByName(name);
			
		return user;
	}
	
	/**
	 * 
	 * @param id role id
	 * @return A list of User objects by given role id
	 */
	public List<User> getUsersByRoleId(int id) {
		
		return userRepository.findByRoleId(id);
		
	}
	
	/**
	 * 
	 * @param id role name
	 * @return A list of User objects by given role name
	 */
	public List<User> getUsersByRoleName(String roleName) {
		
		return userRepository.findByRoleName(roleName);
		
	}
	
	/**
	 * 
	 * @return A list of all User objects
	 */
	public List<User> getAllUsers(){
		
		return userRepository.findAll(); 
	}
	
	/**
	 * 
	 * @param keyword Search Term
	 * @return A list of User objects contain the search term in their stream or names
	 */
    public List<User> searchByStreamOrName(String keyword) {
    	
        if (keyword != null) {
            return userRepository.filterByStreamOrName(keyword);
        }
        return userRepository.findByRoleName("USER");
    }
    
    /**
     * Save a User entity
     * @param user
     */
	public void saveUser(User user) {
		
		userRepository.save(user);
	}
	
	/**
	 * Update a User entity
	 * @param user
	 */
	public void updateUser(User user){
		
		saveUser(user);
	}
	
	/**
	 * Delete a User object by given username
	 * @param username
	 */
	public void deleteUserByUsername(String username) {
		
		userRepository.deleteById(username);
	}
	
	/**
	 * Delete All Users Objects
	 * @return
	 */
	public int deleteAllUsers() {
		
		int count = 0;
		List<User> users = getAllUsers();
		
		for(User user : users) {
			userRepository.delete(user);
			count++;
		}
		
		return count;
		
	}
		
	/**
	 * 
	 * @return A List of all usernames
	 */
	public List<String> getUsernames(){
		
		List<String> usernames = new ArrayList<>();
		
		for (String username : userRepository.getUserNames())
			usernames.add(new String(username));
			
		return usernames;
	}
	
	/**
	 * This method hashes and updates a user's password
	 * @param user
	 * @param newPassword
	 */
    public void changePassword(User user, String newPassword) {
    	
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(newPassword);
	    
        user.setPassword(encodedPassword);

    }
}
	

