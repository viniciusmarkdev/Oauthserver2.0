package com.dev.authorizationserver.security;

import java.util.List;
import java.util.Map;

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
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 
	 * Sobrescreve o método configure() para configurar a instância do
	 * ClientDetailsService
	 * 
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		/*
		 * 
		 * Cria uma instância utilizando a implementação do ClientDetailsService.
		 * 
		 */

		var service = new InMemoryClientDetailsService();

		/*
		 * Cria uma instância de ClientDetails e configura os detalhes necessários sobre
		 * o cliente
		 */

		var cd = new BaseClientDetails();
		cd.setClientId("client");
		cd.setClientSecret("secret");
		cd.setScope(List.of("read"));
		cd.setAuthorizedGrantTypes(List.of("password"));

		/**
		 * Adiciona a instância de ClientDetails ao InMemoryClientDetailsService.
		 */

		service.setClientDetailsStore(Map.of("client", cd));

		/**
		 * Configura o ClientDetailsService para ser usado pelo nosso servidor de
		 * autorização.
		 */
		clients.withClientDetails(service);

	}

	
	/*
	 * 
	 * Forma mais enxuta de configurar 
	 * 
	 * @Override
	   public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("client").secret("secret").authorizedGrantTypes("password").scopes("read");
	    }
	 * 
	 */
	

	/*
	 * 
	 * Agora podemos alterar a classe AuthServerConfig para registrar o
	 * AuthenticationManager
	 * 
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
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

		endpoints.authenticationManager(authenticationManager);
	}

}