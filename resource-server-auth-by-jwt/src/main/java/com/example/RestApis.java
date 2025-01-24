package com.example;

import java.util.Map;
import java.util.HashMap;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class RestApis {
    @RequestMapping("/hello")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println("/foo/hello is invoked");
		if(null!=principal){
			return principal.getAttributes();
		}
		else {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("Hello", "World");
			return returnMap;
		}

	}

}
