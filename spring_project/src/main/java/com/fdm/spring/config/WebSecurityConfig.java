package com.fdm.spring.config;

import javax.sql.DataSource;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
        .authorizeHttpRequests(authorize -> authorize
        	.requestMatchers("/user/**").hasAuthority("ROLE_USER")
        	.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
            .anyRequest().permitAll()
        )
        .formLogin(formLogin -> formLogin
        	.loginPage("/login")
//        	.loginProcessingUrl("/login")
            .defaultSuccessUrl("/login_success")
            .permitAll()
        )
//        .logout(logout -> logout.permitAll())
        .logout(logout -> logout.logoutSuccessUrl("/logout_success"))
        .rememberMe(Customizer.withDefaults());

    return http.build();
    }
	

    

	
	
}
