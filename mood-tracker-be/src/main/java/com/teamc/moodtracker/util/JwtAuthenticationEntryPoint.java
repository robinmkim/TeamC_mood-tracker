package com.teamc.moodtracker.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Spring Security에서 인증에 실패했을 때 처리할 클래스,
// 추상 클래스인 BasicAuthenticationEntryPoint를 상속받아 구현한다.
// Spring Component로 등록한다.

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        response.getWriter().write("{ \"message\": \"" + authException.getMessage() + "\" }");
    }
    // Bean의 속성이 설정된 후에  호출
    @Override
    public void afterPropertiesSet() {
        // setRealName("JWT Authentication")을  호출하여, 인증 영역의 이름을 "JWT Authentication"으로 설정.
        setRealmName("JWT Authentication");
        super.afterPropertiesSet();
    }
}
