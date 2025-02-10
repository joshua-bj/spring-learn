package com.example;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is auth protected URI, after user login at the frontend,
 * call this REST through XHR.
 *
 * It proves from frontend to backend can pass user's credential
 *
 */

@RestController
@RequestMapping("/{tenantId}")
public class RestApisForTestPathVariable {
	private final TokenService tokenService;
	private final ApiService apiService;

	public RestApisForTestPathVariable(TokenService tokenService, ApiService apiService) {
		this.tokenService = tokenService;
		this.apiService = apiService;
	}

    @RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal,
		@PathVariable String tenantId) {
		System.out.println("Tenant id is: " + tenantId);
		System.out.printf("User:%s belong to groups:%s%n",
				principal.getAttributes().get("name"), Utils.listToString(tokenService.getGroupsFromAuthContext()));
		return principal.getAttributes();
	}

	/**
	 * Use this API to verify from backend to backend, how to pass the user's credential
	 *
	 * @param principal
	 * @param tenantId
	 * @return
	 */
	@RequestMapping("/chain-call")
	public String chainCall(@AuthenticationPrincipal OAuth2User principal,
									@PathVariable String tenantId) {
		System.out.println("Tenant id is: " + tenantId);
		String jwt = tokenService.getJwtFromAuthContext();
		System.out.printf("Get current user's JWT: %s%n", jwt);
		return apiService.callApiWithJwt("http://localhost:8082/foo/hello", jwt);
	}

}
