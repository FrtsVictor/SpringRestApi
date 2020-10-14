package com.br.restApi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.restApi.service.auth.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //for enable @PreAutozization at endpoint
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	
	
	//call userDetails to save loginInfo
	@Autowired
	private CustomUserDetailsService customDetailService;
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/*/protected/**").hasRole("USER")
		.antMatchers("/*/admin/**").hasRole("ADMIN")
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
		
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//auth.userDetailsService need a user of type UserDetail we created UserDetailService to use it here
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customDetailService).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}
	

	
	//Global method local Authentication
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("{noop}admin").roles("USER", "ADMIN")
//		.and()
//		.withUser("vct").password("{noop}123321").roles("USER");
//	}
	
//spring security handle de permissions, and we need to say all roles admin isn't user
	
}
