package com.dev.authorizationserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig  extends AuthorizationServerConfigurerAdapter {
	
	
	/**
	 * 
	 * 
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients ) throws Exception {
		
		var service = new InMemoryClientDetailsService();
		
		var cd = new BaseClientDetails();
		
		
	}
	
/*
 * 
 * Agora podemos alterar a classe AuthServerConfig para registrar o AuthenticationManager
 
 * 
 */
	
	/*
	 * 
	 * Injeta o gerenciador de autenticação instância do contexto
	 * 
	 * 
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	/*
	 * Sobrescreve o método configure() para configurar o AuthenticationManager
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints ) {
		
		endpoints.authenticationManager(authenticationManager);
	}
	
	
	
	
	
}