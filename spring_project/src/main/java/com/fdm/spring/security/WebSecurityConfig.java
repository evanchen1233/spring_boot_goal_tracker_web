package com.fdm.spring.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * This class uses a WebSecurity to create the FilterChainProxy 
 * that performs the web based security for Spring Security. 
 * It then exports the necessary beans.
 * @author Yifeng Chen
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
    @Autowired
    private DataSource dataSource;
    
    @Bean
    UserDetailsService userDetailsService() {
    	return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    AuthenticationManager authenticationManager(
             AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
    /**
     * This is the springSecurityFilterChain. It authorizes HTTP requests base on authority.
     * URL "/admin/**" can only been accessed by Admin
     * URL "/user/**" can only been accessed by Trainee
     * URL "/changepassword/**" can only been accessed by logged in users
     * Others can be accessed by anyone
     * @param http
     * @return 
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
        	.requestMatchers("/user/**").hasAuthority("ROLE_USER")
        	.requestMatchers("/changePassword/**").authenticated()
            .anyRequest().permitAll()
        )
        .formLogin(formLogin -> formLogin
        	.loginPage("/login")
        	.loginProcessingUrl("/login")
            .defaultSuccessUrl("/login_success",true)
            .permitAll()
        )
        .logout(logout -> logout
        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));
		
    return http.build();
    }
	

    

	
	
}
