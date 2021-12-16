package com.steam.auth.jwt;

import com.steam.auth.dto.Token;
import com.steam.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final long ACCESS_EXPIRE_TIME = 10 * 60 * 1000;
    private static final long REFRESH_EXPIRE_TIME = 60 * 60 * 1000;
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }

    public Token createAccessToken(User user) {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        Map<String, Object> payload = new HashMap<>();
        payload.put("code", user.getId());
        payload.put("name", user.getNickname());
        payload.put("type", user.getRole() ? "NORMAL" : "SPECIAL");

        long now = (new Date()).getTime();
        Date expireTime = new Date(now + ACCESS_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
