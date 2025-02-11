package com.example;

import com.nimbusds.jwt.JWTClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoIssuerJwtDecoder implements JwtDecoder {

    private final JWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

    public Jwt decode2(String token) {
        try {
            // Process JWT without issuer validation
            var jwtClaims = jwtProcessor.process(token, null);

            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(Map.of("alg", "none"))) // Mock header
                    .claims(c -> c.putAll(jwtClaims.getClaims()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode JWT", e);
        }
    }

    @Override
    public Jwt decode(String token) {
        try {
            // Parse JWT (no signature verification)
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Extract claims
            JWTClaimsSet jwtClaims = signedJWT.getJWTClaimsSet();
            Map<String, Object> claims = new HashMap<String, Object>(jwtClaims.getClaims());
            if(claims.get(JWTClaimNames.ISSUED_AT) instanceof Date) {
                claims.put(JWTClaimNames.ISSUED_AT, ((Date) claims.get(JWTClaimNames.ISSUED_AT)).toInstant());
            }
            if(claims.get(JWTClaimNames.EXPIRATION_TIME) instanceof Date) {
                claims.put(JWTClaimNames.EXPIRATION_TIME, ((Date) claims.get(JWTClaimNames.EXPIRATION_TIME)).toInstant());
            }
            if(claims.get(JWTClaimNames.NOT_BEFORE) instanceof Date) {
                claims.put(JWTClaimNames.NOT_BEFORE, ((Date) claims.get(JWTClaimNames.NOT_BEFORE)).toInstant());
            }
            System.out.println(claims.get("iat").getClass().getName());
            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(Map.of("alg", "none"))) // Mock header
                    .claims(c -> c.putAll(claims))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode JWT", e);
        }
    }
}
