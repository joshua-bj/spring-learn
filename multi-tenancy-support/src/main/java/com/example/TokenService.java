package com.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ToDo: cache the token, to ease pressure on IAM
 */

@Service
public class TokenService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public TokenService(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    /**
     * Get access token from client id and client secret
     *
     * @return access token
     */
    public String getAccessTokenForFunctionalUser() {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("admin") // The registration ID defined in your application.yml
                .principal("client") // A dummy principal; required but doesn't represent a real user
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Failed to retrieve access token");
        }

        return authorizedClient.getAccessToken().getTokenValue();
    }

    /**
     * Get current user's group from authentication context
     *
     * @return the list groups of current user belong to
     */
    public List<String> getGroupsFromAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getClaim("groups"); // Extract groups claim
            }
            else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
                return oidcUser.getAttribute("groups");
            }
        }
        return null;
    }

    /**
     * Get the JWT token from auth context, it can be access token or Id token
     *
     * @return
     */
    public String getJwtFromAuthContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getTokenValue();
            }
            else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
                // this is ID token, there is no access token from DefaultOidcUser
                return oidcUser.getIdToken().getTokenValue();
            }
        }
        return null;
    }
}

