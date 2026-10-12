package com.FullStack.Smart_Data_Retention_System.security;

import com.FullStack.Smart_Data_Retention_System.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider
{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init()
    {
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user)
    {

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("name", user.getName())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromToken(String token)
    {
        return getClaims(token).getSubject();
    }

    public Long getUserIdFromToken(String token)
    {
        return getClaims(token).get("userId", Long.class);
    }

    public String getRoleFromToken(String token)
    {
        return getClaims(token).get("role", String.class);
    }

    public String getNameFromToken(String token)
    {
        return getClaims(token).get("name", String.class);
    }

    public boolean isTokenExpired(String token)
    {
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean validateToken(String token)
    {
        try
        {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return !isTokenExpired(token);
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SecurityException | IllegalArgumentException ex)
        {
            return false;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        String email = getEmailFromToken(token);

        return email.equals(userDetails.getUsername()) && validateToken(token);
    }

    private Claims getClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
