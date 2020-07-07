package com.levimartines.todoapp.util;

import com.levimartines.todoapp.constant.SecurityConstants;
import com.levimartines.todoapp.exceptions.AuthorizationException;
import com.levimartines.todoapp.service.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_JWT)
            .compact();
    }

    public static boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return username != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    public static String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    public static Long getId() {
        CustomUserDetails authUser = authenticated();
        if (authUser != null) {
            return authUser.getId();
        }
        throw new AuthorizationException("Acesso proibido.");
    }

    public static CustomUserDetails authenticated() {
        try {
            return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isOwner(Long userId) {
        CustomUserDetails authUser = authenticated();
        if (authUser == null || !userId.equals(authUser.getId())) {
            return false;
        }
        return true;
    }

    private static Claims getClaims(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET_JWT)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
