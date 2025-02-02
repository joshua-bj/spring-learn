package com.example;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiServiceWithWebClient {
    private final WebClient webClient;

    public ApiServiceWithWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String fetchData(String jwt, String uri) {
        User user =  webClient.get()
                            .uri(uri)
                            .headers(headers -> headers.setBearerAuth(jwt))
                            .retrieve()
                            .bodyToMono(User.class)
                            .block(); // Blocking call, use in non-reactive apps
        System.out.println(user);
        return user.toString();
    }
}
