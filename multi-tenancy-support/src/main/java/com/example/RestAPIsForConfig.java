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

	public RestAPIsForConfig(ClientRegistrationService clientRegistrationService,
							 TokenService tokenService,
							 ApiService apiService) {
		this.clientRegistrationService = clientRegistrationService;
		this.tokenService = tokenService;
		this.apiService = apiService;
	}

    @RequestMapping("/add-client-registry")
	public String addRegistry() {
		clientRegistrationService.addNewClient(ClientRegistrationService.tenant02ClientRegistration());
		return "Client Registry tenant02 has been added";
	}

	@GetMapping("/token")
	public String fetchToken() {
		return tokenService.getAccessToken();
	}

	@GetMapping("/call-resource-server")
	public String callResourceServer() {
		String jwt = tokenService.getAccessToken();
		return apiService.callApiWithJwt(jwt,"http://localhost:8082/foo/hello");
	}
}