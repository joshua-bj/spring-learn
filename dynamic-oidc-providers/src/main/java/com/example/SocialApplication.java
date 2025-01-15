/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootApplication
@Configuration
public class SocialApplication {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.requestMatchers("/oauth2/**", "/login/**", "/error", "/webjars/**","/logout")
				.permitAll()
			.anyRequest().fullyAuthenticated()
				.and()
			.oauth2Login()
				.clientRegistrationRepository(clientRegistrationRepository());
		http						
			.csrf().disable()
			.logout()
			.logoutSuccessUrl("/login.html").permitAll();
			
		return http.build();
	}

	public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(tenantClientRegistration());
    }

    private ClientRegistration tenantClientRegistration() {
        return ClientRegistration.withRegistrationId("tenant01")
            .clientId("spring-boot-client")
            .clientSecret("bV04GWXCHEGeAJhTCyPwm0qLNBqJGd3T")
            .scope("openid", "profile", "offline_access")
            .authorizationUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/auth")
            .tokenUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/token")
            .userInfoUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/userinfo")
			.jwkSetUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/certs")
            .userNameAttributeName("sub")
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
            .build();
    }

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

}
