package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class RestAPIsForConfig {

	private final ClientRegistrationService clientRegistrationService;
	public RestAPIsForConfig(ClientRegistrationService clientRegistrationService) {
		this.clientRegistrationService = clientRegistrationService;
	}

    @RequestMapping("/add-registry")
	public void addRegistry() {
		clientRegistrationService.addNewClient(ClientRegistrationService.tenant02ClientRegistration());
	}

}
