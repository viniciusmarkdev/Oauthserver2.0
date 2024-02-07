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
 * Para testar abrimos o prompt : colocamos o comando abaixo:
 * 
 * curl -v -X POST -u client:secret
 * "http://localhost:8080/oauth/token?grant_type=password&username=marcos&password=12345&scope=read"
 * 
 * 
 * curl: É uma ferramenta de linha de comando utilizada para fazer requisições
 * HTTP.
 * 
 * -v: Ativa o modo verbose (detalhado), exibindo informações completas sobre a
 * solicitação e resposta.
 * 
 * -XPOST: Especifica que a solicitação será do tipo POST, indicando a intenção
 * de enviar dados para o servidor.
 * 
 * -u client:secret: Envia as credenciais do cliente no formato "client:secret"
 * usando a autenticação HTTP Basic. Isso é comum em solicitações OAuth 2 para
 * autenticar o cliente.
 * 
 * Aqui a resposta
 * 
 * {"access_token":"aac0024e-1311-4bed-80ff-771169e66990","token_type":"bearer",
 * "expires_in":43199,"scope":"read"}*
 * 
 * Com a configuração padrão no Spring Security, um token é um simples UUID
 * 
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
	 *
	 * Usando o tipo de concessão de senha. usamos o servidor de
	 * autorização com o OAuth 2 tipo de concessão de senha
	 * Podemos solicitar um token no endpoint /oauth/token. O Spring Security
	 * configura automaticamente este endpoint para nós. Usamos as credenciais do
	 * cliente com HTTP Basic para acessar o endpoint e enviar o necessário.
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