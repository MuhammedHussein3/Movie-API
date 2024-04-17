package com.movieflix.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl {

    private static final String SECRET_KEY = "L24FDKJSLKJLKWJ4L2KRLKD23JSAL33KJFLKJLKWJ13RLKJCLKJZK234221353JFSFEFAKER";
    //1. extract username from jwt
    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims , T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    //2. extract information from jwt
    private Claims extractClaims(String token){
        return Jwts
                .parserBuilder().
                setSigningKey(getSingKey()).
                build().
                parseClaimsJws(token)
                .getBody();
    }

    //3. decode and get the key

    private Key getSingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>() , userDetails);
    }
    //4. generate token using Jwt utility class and return token as String
    public String generateToken(Map<String,Objects> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2000 * 60 * 24))
                .signWith(getSingKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    //if token is valid by checking if token is expired for current user
    public boolean isTokenValid(String token , UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //get expiration data from token
    private Date extractExpiration(String token) {

        return  extractClaim(token,Claims::getExpiration);
    }
}
