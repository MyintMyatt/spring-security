package com.app.service.impl;

import com.app.model.entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {


    private Dotenv dotenv;

    private  static String  SECRET_KEY = "";

    public JwtService() {
        dotenv = Dotenv.configure().filename(".env").load();
        SECRET_KEY = dotenv.get("jwt_secrect_key");
        System.out.println("SECRET_KEY => " + SECRET_KEY);
    }

    public String generateToken(User user) {
        String token = Jwts.builder()
                .subject(user.getUserEmail())
                .claim("roles",user.getRoles())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30 ))
                .signWith(getSingInKey())
                .compact();
        return token;
    }


    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private SecretKey getSingInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return expriationDate(token).before(new Date());
    }

    private Date expriationDate(String token){
        return extractClaims(token,Claims::getExpiration);
    }
}
