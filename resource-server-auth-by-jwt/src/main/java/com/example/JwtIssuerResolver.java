package com.example;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Get a JwtDecoder by JWT's iss claim dynamically
 */

@Component
public class JwtIssuerResolver {

    // Cache decoders for performance
    private final Map<String, JwtDecoder> jwtDecoderCache = new ConcurrentHashMap<>();

    public JwtDecoder getJwtDecoder(String issuer) {
        // Get JWK Set URI based on issuer
        String jwkSetUri = issuer + "/protocol/openid-connect/certs";

        // Use cached decoder if available
        return jwtDecoderCache.computeIfAbsent(jwkSetUri, uri -> NimbusJwtDecoder.withJwkSetUri(uri).build());
    }
}
