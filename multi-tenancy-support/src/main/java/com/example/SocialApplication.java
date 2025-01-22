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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;

import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class SocialApplication {

	private final CustomSecurityFilter customSecurityFilter;

    public SocialApplication(CustomSecurityFilter customSecurityFilter) {
        this.customSecurityFilter = customSecurityFilter;
    }

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests()
			.requestMatchers("/oauth2/**", "/login/**", "/error", "/webjars/**","/logout","/*")
				.permitAll()
			.anyRequest().fullyAuthenticated()
				.and()
			.addFilterBefore(customSecurityFilter, SecurityContextHolderFilter.class)
		//	.exceptionHandling().authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/login/oauth2"))
			.oauth2Login()
				.loginPage("/login/oauth2")
				.clientRegistrationRepository(clientRegistrationRepository());
		http						
			.csrf().disable()
				.cors().configurationSource(corsConfigurationSource())
				.and()
//				.logout().logoutSuccessUrl("http://localhost:8080/realms/tenant01/protocol/openid-connect/logout").permitAll();
			.logout()
				.logoutSuccessHandler(oidcLogoutSuccessHandler()) // Custom logout handler
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.permitAll();
			
		return http.build();
	}

	public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(tenant01ClientRegistration(), tenant02ClientRegistration());
    }

    private ClientRegistration tenant01ClientRegistration() {
		Map<String, Object> configurationMetadata = new HashMap<String, Object>();
		configurationMetadata.put("end_session_endpoint", "http://localhost:8080/realms/tenant01/protocol/openid-connect/logout");
        return ClientRegistration.withRegistrationId("tenant01")
            .clientId("spring-boot-client")
            .clientSecret("B1ROQ53Oo5ODN2N1z27rJat1JP0ufaBG")
            .scope("openid", "profile", "offline_access")
            .authorizationUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/auth")
            .tokenUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/token")
            .userInfoUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/userinfo")
			.jwkSetUri("http://localhost:8080/realms/tenant01/protocol/openid-connect/certs")
			.providerConfigurationMetadata(configurationMetadata)
            .userNameAttributeName("sub")
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
            .build();
    }

	private ClientRegistration tenant02ClientRegistration() {
		Map<String, Object> configurationMetadata = new HashMap<String, Object>();
		configurationMetadata.put("end_session_endpoint", "http://localhost:8080/realms/tenant02/protocol/openid-connect/logout");
		return ClientRegistration.withRegistrationId("tenant02")
				.clientId("spring-boot-client")
				.clientSecret("WauO7zGLQzSLKSxFC4ZZb5SHVrLNguCJ")
				.scope("openid", "profile", "offline_access")
				.authorizationUri("http://localhost:8080/realms/tenant02/protocol/openid-connect/auth")
				.tokenUri("http://localhost:8080/realms/tenant02/protocol/openid-connect/token")
				.userInfoUri("http://localhost:8080/realms/tenant02/protocol/openid-connect/userinfo")
				.jwkSetUri("http://localhost:8080/realms/tenant02/protocol/openid-connect/certs")
				.providerConfigurationMetadata(configurationMetadata)
				.userNameAttributeName("sub")
				.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
				.authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
				.build();
	}

	public OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
		// Configure the logout success handler to redirect to Keycloak logout endpoint
		OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler =
				new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository());

		// Optional: Set the post-logout redirect URI (where the user is redirected after logout)
		logoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8081");

		return logoutSuccessHandler;
	}

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:9081");
		configuration.addAllowedMethod("*"); // Allow all HTTP methods
		configuration.addAllowedHeader("*"); // Allow all headers
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("http://localhost:8080/**", configuration);
		return source;
	}

}
