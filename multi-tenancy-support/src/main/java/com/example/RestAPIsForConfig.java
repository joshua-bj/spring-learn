package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/config")
public class RestAPIsForConfig {

	private final ClientRegistrationService clientRegistrationService;
	public RestAPIsForConfig(ClientRegistrationService clientRegistrationService) {
		this.clientRegistrationService = clientRegistrationService;
	}

    @RequestMapping("/add-client-registry")
	public String addRegistry() {
		clientRegistrationService.addNewClient(ClientRegistrationService.tenant02ClientRegistration());
		return "Client Registry tenant02 has been added";
	}

}
