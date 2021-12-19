package com.steam.view.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.security.Key;

@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Boolean isLogin(String token) {
        return !token.isBlank();
    }

    public Boolean isAdmin(String token) {
        if(token.isBlank())
            return false;

        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().get("type", String.class).equals("SPECIAL");
        }
        catch (RuntimeException e) {
            return false;
        }
    }

    public String getAccessTokenByCookies(Cookie[] cookies) {
        if(cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("accessToken"))
                    return cookie.getValue();

        return "";
    }
}