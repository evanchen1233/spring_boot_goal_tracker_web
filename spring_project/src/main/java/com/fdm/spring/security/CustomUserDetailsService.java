package com.fdm.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fdm.spring.dal.UserRepository;
import com.fdm.spring.model.User;

/**
 * This class is used throughout the framework as a user DAO 
 * and is the strategy used by the DaoAuthenticationProvider.
 * @author Yifeng Chen
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
     
    /**
     * Locates the user based on the username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
 
    
}