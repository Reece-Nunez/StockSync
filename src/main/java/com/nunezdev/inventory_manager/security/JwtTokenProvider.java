package com.nunezdev.inventory_manager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public Long getUserIdFromJWT(String token) {
        DecodedJWT jwt = JWT.decode(token);
        try {
            return Long.parseLong(jwt.getSubject());
        } catch (NumberFormatException ex) {
            logger.error("Could not parse user ID from JWT.", ex);
            return null;
        }
    }

    public boolean validateToken(String authToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(authToken);
            return true;
        } catch (JWTVerificationException ex) {
            logger.error("JWT toke validation failed.", ex);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        // Define the algorithm with the same secret you used to sign the JWT
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        // Build the verifier using the specified algorithm
        JWTVerifier verifier = JWT.require(algorithm).build();

        // Verify the token and decode it
        DecodedJWT decodedJWT = verifier.verify(token);

        // Extract the username (subject) and granted authorities from the decoded JWT
        String username = decodedJWT.getSubject();
        // In this example, we just use a single authority. Modify this to suit your needs.
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // Create a principal with the username and authorities
        User principal = new User(username, "", authorities);

        // Return an Authentication token with the principal, credentials (token), and authorities
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
