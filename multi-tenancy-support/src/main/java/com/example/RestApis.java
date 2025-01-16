package com.example;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{tenantid}")
public class RestApis {
    @RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal, 
		@PathVariable String tenantid) {
		System.out.println("Tenant id is: " + tenantid);
		return principal.getAttributes();
	}

}
