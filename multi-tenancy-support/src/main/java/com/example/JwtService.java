package com.example;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtService {

    public List<String> getUserGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof Jwt jwt) {
                return jwt.getClaim("groups"); // Extract email claim
            }
            else if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
                return oidcUser.getAttribute("groups");
            }
        }
        return null;
    }
}
