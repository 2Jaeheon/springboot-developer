package org.jaeheon.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jaeheon.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // Token Generation Method
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        // Create a token with the current time and the expiration time.
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // Token Generation Method
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header: JWT Type
            // issuer : The issuer of the token
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(now) // IssuedAt: The time the token was issued
            .setExpiration(expiry) // Expiration: The expiration time of the token
            .setSubject(user.getEmail()) // Subject: The subject of the token
            .claim("id", user.getId()) // Claim id : The id of the user
            // Signature: Encrypt the hash value along with the secret value using HS256 method.
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .compact();
    }

    // Token validation method
    // Token decryption is in progress. Returns false if an error occurs, true if normal.
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method that receives a token and returns an Authentication object containing authentication information.
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
            new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
            new org.springframework.security.core.userdetails.User(claims.getSubject(), "",
                authorities), token, authorities);
    }


    // Method to get user ID based on token
    // After decrypting with the secret key saved in the properties file, claim information is returned,
    // and the value saved as the id key is retrieved from the claim and returned.
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }
}
