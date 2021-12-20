package com.steam.gateway.jwt;

import com.steam.gateway.exception.UseJwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}")String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @UseJwt
    public Boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            e.getStackTrace();
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            e.getStackTrace();
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            e.getStackTrace();
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    @UseJwt
    public Boolean isAdmin(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return claims.getBody().get("type", String.class).equals("SPECIAL");
    }

    public String getAccessTokenByCookies(Cookie[] cookies) {
        if(cookies == null)
            return "";

        for (Cookie cookie : cookies)
            if (cookie.getName().equals("accessToken"))
                return cookie.getValue();

        return "";
    }

    public String getAccessTokenInQueryString(String query) {
        try {
            System.out.println(query);
            String[] queries = query.split("&");
            for(String q : queries)
                if(q.startsWith("Authorization="))
                    return q.substring("Authorization=".length());
        }
        catch (RuntimeException e) {
            return "";
        }
        return "";
    }


    public String getAccessTokenInRequest(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        try {
            return request.getHeader("Authorization").substring("Bearer ".length());
        }
        catch (RuntimeException ignored) {
            return "";
        }
    }
}
