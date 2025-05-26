package com.ssafy.enjoytrip.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    //private final String secretKey = "example-secret-key-should-be-at-least-32-characters"; // .env에 분리
    @Value("${jwt.secret}")
    private String secretKey;

    private final long accessTokenExpiration = 60 * 60 * 3000L; // 3시간


    //토큰 생성 수정
    public String createToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId); // ✅ 여기 subject는 long → String으로 전달됨
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    // 사용자 이름 추출
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    //사용자 ID 추출
    public String getUserId(String token) {
        return parseClaims(token).getSubject(); // 이제는 userId가 들어 있음!
    }

    // roles 정보 추출 (선택)
    public List<String> getRoles(String token) {
        Claims claims = parseClaims(token);
        return (List<String>) claims.get("roles");
    }


    // 이메일 꺼내기
    public String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 내부용 Claims 파싱
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
