package com.fdm.spring.security;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fdm.spring.model.User;

/**
 * This class simply store user information which is later encapsulated 
 * into Authentication objects
 * @author Yifeng Chen
 *
 */
public class CustomUserDetails implements UserDetails {
	 
	String ROLE_PREFIX = "ROLE_";
    private User user;

    
    public CustomUserDetails(User user) {
        this.user = user;
    }
 
    /**
     * This method get user's authority base on role 
     */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getRoleName()));

        return list;
	}
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }


 
}
