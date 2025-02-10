package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// this is the REST api which do not need authentication

@RestController
@RequestMapping("/config")
public class RestAPIsForConfig {

	private final ClientRegistrationService clientRegistrationService;
	private final TokenService tokenService;
	private final ApiService apiService;
	private final ApiServiceWithWebClient apiServiceWithWebClient;

	public RestAPIsForConfig(ClientRegistrationService clientRegistrationService,
							 TokenService tokenService,
							 ApiService apiService,
							 ApiServiceWithWebClient apiServiceWithWebClient) {
		this.clientRegistrationService = clientRegistrationService;
		this.tokenService = tokenService;
		this.apiService = apiService;
		this.apiServiceWithWebClient = apiServiceWithWebClient;
	}

    @RequestMapping("/add-client-registry")
	public String addRegistry() {
		clientRegistrationService.addNewClient(ClientRegistrationService.tenant02ClientRegistration());
		return "Client Registry tenant02 has been added";
	}

	@GetMapping("/token")
	public String fetchToken() {
		return tokenService.getAccessTokenForFunctionalUser();
	}

	@GetMapping("/call-resource-server")
	public String callResourceServer() {
		String jwt = tokenService.getAccessTokenForFunctionalUser();
		return apiService.callApiWithJwt("http://localhost:8082/foo/hello");
	}

	@GetMapping("/call-resource-server2")
	public String callResourceServer2() {
		String jwt = tokenService.getAccessTokenForFunctionalUser();
		return apiServiceWithWebClient.fetchData("http://localhost:8082/foo/hello2");
	}
}