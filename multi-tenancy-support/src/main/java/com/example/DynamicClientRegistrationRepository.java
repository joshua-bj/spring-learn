package com.example;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// replace InMemoryClientRegistrationRepository because InMemoryClientRegistrationRepository is immutable
public class DynamicClientRegistrationRepository implements ClientRegistrationRepository {

    private final Map<String, ClientRegistration> clientRegistrations = new ConcurrentHashMap<>();

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        return clientRegistrations.get(registrationId);
    }

    public void addClientRegistration(ClientRegistration clientRegistration) {
        clientRegistrations.put(clientRegistration.getRegistrationId(), clientRegistration);
    }

    public void removeClientRegistration(String registrationId) {
        clientRegistrations.remove(registrationId);
    }
}

