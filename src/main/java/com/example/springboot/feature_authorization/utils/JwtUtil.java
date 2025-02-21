package com.example.springboot.feature_authorization.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class JwtUtil {

    private static final String SECRET =
            "yourSuperSecretKeyyourSuperSecretKeyyourSuperSecretKeyyourSuperSecretKey";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Private constructor to prevent instantiation
    private JwtUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * generates a jwt token by adding username, and roles to it
     *
     * @param username
     * @param roles
     * @param userId
     * @return
     */
    public static String generateToken(String username, List<String> roles, String userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * validates a jwt token
     *
     * @param token
     * @param username
     * @return
     */
    public static boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    /**
     * extracts roles from the jwt token
     *
     * @param token
     * @return
     */
    public static List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    /**
     * extracts userId from the jwt token
     *
     * @param token
     * @return
     */
    public static Long extractUserId(String token) {
        return extractClaim(token, claims -> Long.parseLong(claims.get("userId", String.class)));
    }

    /**
     * extracts username from the jwt token
     *
     * @param token
     * @return
     */
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * extracts expiration date and time from the jwt token
     *
     * @param token
     * @return
     */
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * returns whether the token is expired or not
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * extracts claims from the jwt token
     *
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        return claimsResolver.apply(claims);
    }
}
