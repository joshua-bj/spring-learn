package com.example;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class RestApis {
    @RequestMapping("/hello")
	public Map<String, Object> hello() {
		System.out.println("/foo/hello is invoked");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			if (authentication.getPrincipal() instanceof Jwt jwt) {
				List<String> groups = jwt.getClaim("groups");
				System.out.println("Get groups:" + Utils.listToString(groups));
			}
			else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
				// this is ID token, there is no access token from DefaultOidcUser
				List<String> groups = oidcUser.getAttribute("groups");
				System.out.println("Get groups:" +Utils.listToString(groups));
			}
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("Hello", "World");
		return returnMap;
	}

	/**
	 * Test return a Java data model
	 *
	 * @return
	 */
	@RequestMapping("/hello2")
	public User hello2() {
		return new User("joshua", 28);
	}
}
