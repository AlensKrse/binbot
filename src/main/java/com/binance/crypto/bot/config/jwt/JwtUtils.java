package com.binance.crypto.bot.config.jwt;

import com.binance.crypto.bot.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtils {

    @Value("${app.jwt.token.secret.key.path}")
    String jwtTokenSecretKeyPath;

    @Value("${app.jwt.token.expiration.min}")
    int jwtTokenExpirationMin;


    public String generateJwtToken(final Authentication authentication) {
        final UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.plusMinutes(new Date(), jwtTokenExpirationMin))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = getSecretKeyBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private byte[] getSecretKeyBytes() {
        try {
            final String secretKey = FileUtils.readFileToString(new File(jwtTokenSecretKeyPath), StandardCharsets.UTF_8);
            return Decoders.BASE64.decode(secretKey);
        } catch (final IOException e) {
            throw new UnsupportedOperationException(String.format("Unable to load secret key content from file, path: '%s'", jwtTokenSecretKeyPath), e);
        }
    }

    public String getUserNameFromJwtToken(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKeyBytes()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
