package com.example;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{tenantId}")
public class RestApisForTestPathVariable {
	private final JwtService jwtService;

	public RestApisForTestPathVariable(JwtService jwtService) {
		this.jwtService = jwtService;
	}

    @RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal,
		@PathVariable String tenantId) {
		System.out.println("Tenant id is: " + tenantId);
		System.out.printf("User:%s belong to groups:%s%n",
				principal.getAttributes().get("name"), Utils.listToString(jwtService.getUserGroups()));
		return principal.getAttributes();
	}

}
