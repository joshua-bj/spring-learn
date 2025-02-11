package com.example;

import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Decode a string type JWT to org.springframework.security.oauth2.jwt.Jwt
 * without signature verification
 *
 * if there is a performance issue, consider only return iss claim,
 * as we only use iss at the following logic
 */
public class NoIssuerJwtDecoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) {
        try {
            // Parse JWT (no signature verification)
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Extract claims
            JWTClaimsSet jwtClaims = signedJWT.getJWTClaimsSet();
            Map<String, Object> claims = new HashMap<String, Object>(jwtClaims.getClaims());
            // nimbusds parse iat,exp,nbf to java.util.Date,
            // while spring only accept modern java.time.Instant
            if(claims.get(JWTClaimNames.ISSUED_AT) instanceof Date) {
                claims.put(JWTClaimNames.ISSUED_AT, ((Date) claims.get(JWTClaimNames.ISSUED_AT)).toInstant());
            }
            if(claims.get(JWTClaimNames.EXPIRATION_TIME) instanceof Date) {
                claims.put(JWTClaimNames.EXPIRATION_TIME, ((Date) claims.get(JWTClaimNames.EXPIRATION_TIME)).toInstant());
            }
            if(claims.get(JWTClaimNames.NOT_BEFORE) instanceof Date) {
                claims.put(JWTClaimNames.NOT_BEFORE, ((Date) claims.get(JWTClaimNames.NOT_BEFORE)).toInstant());
            }
            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(Map.of("alg", "none"))) // Mock header
                    .claims(c -> c.putAll(claims))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode JWT", e);
        }
    }
}
