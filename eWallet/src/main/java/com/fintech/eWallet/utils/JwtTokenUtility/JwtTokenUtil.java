package com.fintech.eWallet.utils.JwtTokenUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secret;
    private final long accessTokenExpirationTime = 3600000; // 1 hour
    private final long refreshTokenExpirationTime = 604800000; // 1 week
    public JwtTokenUtil(@Value("${jwt.secret-key}") String secret) {
        this.secret = secret;
    }

    public String generateAccessToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationTime);
        return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationTime);
        return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public void keyGenerator() {
        // Generate a secure key for HS512 algorithm with a size of 512 bits
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Print the encoded key as a string
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated HS512 Key: " + encodedKey);
    }
}
