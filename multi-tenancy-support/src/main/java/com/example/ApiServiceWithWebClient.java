package com.example;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiServiceWithWebClient {
    private final WebClient webClient;
    private final TokenService tokenService;

    public ApiServiceWithWebClient(WebClient webClient, TokenService tokenService) {
        this.webClient = webClient;
        this.tokenService = tokenService;
    }

    public String fetchData(String uri) {
        String jwt = tokenService.getAccessTokenForFunctionalUser();
        User user =  webClient.get()
                            .uri(uri)
                            .headers(headers -> headers.setBearerAuth(jwt))
                            .retrieve()
                            .bodyToMono(User.class)
                            .block(); // Blocking call, use in non-reactive apps
        System.out.println(user);
        return user != null ? user.toString() : null;
    }
}
