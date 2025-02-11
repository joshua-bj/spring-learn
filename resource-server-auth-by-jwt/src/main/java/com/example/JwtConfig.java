package com.example;

import com.nimbusds.jwt.JWTClaimNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtConfig {

    private final JwtIssuerResolver jwtIssuerResolver;

    public JwtConfig(JwtIssuerResolver jwtIssuerResolver) {
        this.jwtIssuerResolver = jwtIssuerResolver;
    }

    /**
     * Get a JwtDecoder by JWT's iss claim dynamically
     *
     * @return
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return token -> {
            // Parse JWT without verifying signature (to extract issuer)
            Jwt unverifiedJwt = noIssuerJwtDecoder().decode(token);

            // Get issuer from token
            String issuer = unverifiedJwt.getClaim(JWTClaimNames.ISSUER);
            if (issuer == null) {
                throw new IllegalArgumentException("JWT missing 'iss' claim");
            }

            // Select correct JwtDecoder based on issuer
            JwtDecoder jwtDecoder = jwtIssuerResolver.getJwtDecoder(issuer);
            return jwtDecoder.decode(token);
        };
    }

    public NoIssuerJwtDecoder noIssuerJwtDecoder() {
        return new NoIssuerJwtDecoder();
    }
}

