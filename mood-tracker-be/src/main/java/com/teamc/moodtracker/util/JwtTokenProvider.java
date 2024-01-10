package com.teamc.moodtracker.util;

import com.teamc.moodtracker.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Date;

@Component
// @Slf4j는 lombok의 어노테이션으로, 로그를 위한 코드를 자동으로 생성해준다.
@Slf4j
public class JwtTokenProvider {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // 인증된 사용자에 대한 JWT를 생성을 하고
    public String createToken(Authentication authentication) {
        // Spring Security에서 Authentication 객체로부터
        // 사용자의 UserDetails를 얻어 사용자 이름을 주제(subject)로 설정하고,
        // 현재 시간과 만료 시간을 포함한 토큰을 생성
        MemberDto memberDetails = (MemberDto) authentication.getPrincipal();
        System.out.println(("member name: " + memberDetails.getUsername()));
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(memberDetails.getUsername())
                .claim("m_id", memberDetails.getM_id())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        System.out.println("Received token for validation: " + token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println(
                    "Validated token: " + Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token));
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token : 토큰의 형식이 올바르지 않음 ");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token : 토큰이 만료되었을 때 발생");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token : 지원되지 않는 토큰 유형일 때 발생");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty : 토큰이 비어있거나 null일 때 발생");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token : 토큰의 서명이 유효하지 않을 때 발생");
        }

        return false;
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public int getMemberId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 'm_id' 클레임을 추출하고 int 타입으로 반환
        return claims.get("m_id", Integer.class);
    }
}
