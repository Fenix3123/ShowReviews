package com.coderscampus.showreviews.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired 
	private PasswordEncoder passwordEncoder;
	@Autowired 
	private UserDetailsService userDetailsService;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
   //this method below does authorization, the login info gets entered here
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth
	   .userDetailsService(userDetailsService)
	   .passwordEncoder(passwordEncoder);
//	   .inMemoryAuthentication()
//	   .withUser("fenix.com")
//	   .password("$2a$10$dzZDx5e5tU10q4XnSQKIW.L.GUV6XCrZmOrocMU8Fi/gc6dTan.iq")
//	   .roles("USER", "ADMIN");
   }
   //this method does authentication
   @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		 .authorizeRequests()
		 	.antMatchers("/admin/**").hasAnyRole("ADMIN")
		 	.antMatchers("/dashboard").hasAnyRole("USER")
		 	.antMatchers("/dashboard2").hasAnyRole("USER")
		 	.antMatchers("/register").anonymous().and()
		 .formLogin()
		 	.loginPage("/login")
		 	.defaultSuccessUrl("/dashboard")
		 	.permitAll();
		 	
	}
}
