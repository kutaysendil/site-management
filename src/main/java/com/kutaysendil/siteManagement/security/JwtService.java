package com.kutaysendil.siteManagement.security;


import com.kutaysendil.siteManagement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractClaims(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("claims", List.class);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

            // Roller ve Permission'ları ayrı ayrı ekle
            Set<String> roles = customUserDetails.getRoles();
            Set<String> permissions = customUserDetails.getPermissions();

            extraClaims.put("roles", roles);
            extraClaims.put("permissions", permissions);

            // Kullanıcı bilgilerini ekle
            User user = customUserDetails.getUser();
            extraClaims.put("userId", user.getId());
            extraClaims.put("name", user.getName());
            extraClaims.put("surname", user.getSurname());

            // Authority'leri claims olarak ekle (geriye dönük uyumluluk için)
            List<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            extraClaims.put("claims", authorities);
        }
        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        Claims claims;
        try {
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT SERVIS HATASI : Beklenmeyen bir hata oluştu. Hata Mesajı Detayı : {}", e.getMessage());
            claims = e.getClaims();
        }
        return claims;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Permission kontrolü için yeni metod
    public boolean hasPermission(String token, String permission) {
        Claims claims = extractAllClaims(token);
        List<String> permissions = claims.get("permissions", List.class);
        return permissions != null && permissions.contains(permission);
    }

    // Role kontrolü için yeni metod
    public boolean hasRole(String token, String roleName) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null && roles.contains(roleName);
    }
}