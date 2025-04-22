package com.weblab4.opilab3.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;

public class JWTUtil {
    protected static final String SECRET_KEY = Base64.getEncoder().encodeToString("stupid_nigga_1488".getBytes());

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // токен действует 1 час
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public static String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
