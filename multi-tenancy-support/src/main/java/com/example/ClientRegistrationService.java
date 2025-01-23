package com.example;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientRegistrationService {

    private final DynamicClientRegistrationRepository repository;

    public ClientRegistrationService() {
        this.repository = new DynamicClientRegistrationRepository();
        repository.addClientRegistration(ClientRegistrationService.tenant01ClientRegistration());
    }

    public void addNewClient(ClientRegistration clientRegistration) {
        repository.addClientRegistration(clientRegistration);
    }

    public void removeClient(String registrationId) {
        repository.removeClientRegistration(registrationId);
    }

    public DynamicClientRegistrationRepository getRepository(){
        return repository;
    }

    public static ClientRegistration tenant01ClientRegistration() {
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

    public static ClientRegistration tenant02ClientRegistration() {
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
}

