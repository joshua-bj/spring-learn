package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

/**
 * Call External REST API with proper authentication
 *
 */

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public ApiService(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    /**
     * Use the configured functional user's credential to invoke API.
     *
     * @param apiUrl
     * @return
     */
    public String callApiWithJwt(String apiUrl) {
        String jwt = tokenService.getAccessTokenForFunctionalUser();
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt); // Automatically formats as "Bearer <token>"
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    /**
     * Invoke API with any JWT, if JWT is null, then use functional user's
     * credential
     *
     * @param apiUrl
     * @param jwt
     * @return
     */
    public String callApiWithJwt(String apiUrl, String jwt) {
        if(null==jwt) {
            // this stand-for no user context credential input
            // let's switch to functional user
            jwt = tokenService.getAccessTokenForFunctionalUser();
        }
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt); // Automatically formats as "Bearer <token>"
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);

        HttpEntity<String> request = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<String> response=null;
        try {
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("Client Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            System.err.println("Server Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            System.err.println("Network Error: " + e.getMessage());
        } catch (RestClientException e) {
            System.err.println("General Error: " + e.getMessage());
        }
        if(null!=response) {
            return response.getBody();
        }
        else {
            return null;
        }

    }
}
