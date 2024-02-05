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

/*
 * 
 * Podemos solicitar um token no endpoint /oauth/token. O Spring Security
 * configura automaticamente esse endpoint para nós. Utilizamos as credenciais
 * do cliente com HTTP Basic para acessar o endpoint e enviar as informações
 * necessárias.
 * 
 * Para testar abrimos  o prompt : colocamos o comando  abaixo:
 * 
 * curl -v -XPOST -u client:secret
 * http://localhost:8080/oauth/token?grant_type=password&username=marcos&password=
 * 12345&scope=read
 */
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	/**
	 * 
	 * Sobrescreve o método configure() para configurar a instância do
	 * ClientDetailsService
	 * 
	 */

	/*
	 * 
	 * 
	 * Se estivermos escrevendo uma implementação na qual armazena detalhes do
	 * cliente em um banco de dados, o que é geralmente o caso em cenários do mundo
	 * real, então é melhor usar a configuração abaixo
	 * 
	 * 
	 * 
	 */

	/*
	 * 
	 * Using the password grant type In this section, we use the authorization
	 * server with the OAuth 2 password grant. Well, we mainly test if it’s working,
	 * because with the implementation we did in sections 13.2 and 13.3, we already
	 * have a working authorization server that uses the password grant type. I told
	 * you it’s easy! Figure 13.5 reminds you of the password grant type and the
	 * place of the authorization server within this flow. Now, let’s start the
	 * application and test it. We can request a token at the /oauth/ token
	 * endpoint. Spring Security automatically configures this endpoint for us. We
	 * use the client credentials with HTTP Basic to access the endpoint and send
	 * the needed
	 * 
	 * 
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
	 * @Override public void configure(ClientDetailsServiceConfigurer clients)
	 * throws Exception {
	 * clients.inMemory().withClient("client").secret("secret").authorizedGrantTypes
	 * ("password").scopes("read"); }
	 * 
	 */

	/*
	 * 
	 * Estamos utilizando o tipo de concessão de senha (password grant) no servidor
	 * de autorização com OAuth 2. Estamos testando se está funcionando, porque com
	 * a implementação que fizemos com github e facebook já temos um servidor de
	 * autorização funcional que utiliza o tipo de concessão de senha.
	 * 
	 * 
	 * 
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