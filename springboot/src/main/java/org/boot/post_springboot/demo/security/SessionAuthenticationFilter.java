package org.boot.post_springboot.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionAuthenticationFilter extends OncePerRequestFilter {

    // 지연 로딩을 위해 ObjectProvider를 사용합니다.
    @Autowired
    private ObjectProvider<UserService> userServiceProvider;

    @Autowired
    private ObjectProvider<CustomUserDetailsService> customUserDetailsServiceProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();

        // 요청의 쿠키들 중에서 "SESSION_ID" 쿠키를 찾습니다.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        // 세션 ID가 존재하고 유효한 경우 인증을 처리합니다.
        if (sessionId != null && userServiceProvider.getObject().isValidSession(sessionId)) {
            // 세션 ID로부터 사용자 이메일을 가져옵니다.
            String userEmail = userServiceProvider.getObject().getUserEmailFromSession(sessionId);
            if (userEmail != null) {
                // 사용자 이메일로 UserDetails를 로드합니다.
                UserDetails userDetails = customUserDetailsServiceProvider.getObject().loadUserByUsername(userEmail);
                // 인증 객체를 생성합니다.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // SecurityContext에 인증 정보를 설정합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 필터 체인의 다음 필터로 요청을 전달합니다.
        filterChain.doFilter(request, response);
    }
}