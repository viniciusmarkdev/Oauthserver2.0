package com.dev.authorizationserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/*
 * 
 * Estender o WebSecurityConfigurerAdapter permite acessar a instância do AuthenticationManager
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*
	 * 
	 * Busca e criar um usuário na memória
	 * 
	 */
	@Bean
	public UserDetailsService uds() {
		
		var uds = new InMemoryUserDetailsManager();
		
		
		var u = User.withUsername("marcos")
				.password("12345")
				.authorities("read")
				.build();
		
		uds.createUser(u);
	

		return uds;
	}
	
	/*
	 * 
	 * Valida o usuário por senha
	 */
	@Bean
	public PasswordEncoder passowordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*
	 * 	Adiciona a instância do AuthenticationManager como um bean no contexto do Spring
	 */
	
	@Bean
	public AuthenticationManager authenticationManagerBean()  throws Exception{
		
		return super.authenticationManagerBean();
		
	}

	
}
