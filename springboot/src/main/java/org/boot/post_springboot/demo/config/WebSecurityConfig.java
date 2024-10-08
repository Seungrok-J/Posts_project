package org.boot.post_springboot.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // CORS 설정: 특정 도메인에서만 리소스에 접근할 수 있도록 설정
////                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                // CSRF 보호 비활성화: 비동기 요청(API)에 대한 보안 위협 방지 기능을 비활성화
//                .csrf(AbstractHttpConfigurer::disable)
//                // HTTP 요청 권한 설정
//                .authorizeHttpRequests(requests -> requests
//                        // "/" 및 "/home", "/public/**" 경로는 비회원(익명 사용자)도 접근 가능
//                        .requestMatchers("/", "/home", "/public/**","/api/**").permitAll()
//                        // 나머지 모든 요청은 인증된 사용자만 접근 가능
//                        .anyRequest().authenticated()
//                )
//                // 로그인 페이지 설정: "/login" 경로로 로그인 페이지 제공, 모든 사용자 접근 가능
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                // 로그아웃 설정: 로그아웃 요청은 모든 사용자에게 허용
//                .logout(LogoutConfigurer::permitAll)
//                // 세션 관리 설정
//                .sessionManagement(management -> management
//                        // 세션 인증 전략 설정 (sessionAuthenticationStrategy() 메서드를 통해 세션 인증 전략 적용)
//                        .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
//                        // 세션 생성 정책 설정: 인증이 필요한 경우에만 세션을 생성 (기본 값)
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                        // 유효하지 않은 세션일 때 리디렉션할 URL 설정
//                        .invalidSessionUrl("/login?invalid")
//                        // 최대 세션 수 제한: 한 사용자는 한 번에 하나의 세션만 허용
//                        .maximumSessions(1)
//                        // 세션 만료 시 리디렉션할 URL 설정
//                        .expiredUrl("/login?expired")
//                        // 최대 세션 수 초과 시 로그인 방지 설정: 이미 로그인이 되어 있는 경우 새로운 로그인 시도 방지
//                        .maxSessionsPreventsLogin(true)
//                );
//        return http.build();
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*")); // 모든 도메인에서의 접근을 허용
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용된 HTTP 메소드
//        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
//        configuration.setAllowCredentials(true); // 크레덴셜 허용
//        configuration.setMaxAge(3600L); // 사전 요청(precache) 결과를 3600초 동안 캐시
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
//        return source;
//    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        // 세션 고정 보호 전략 설정
        return new SessionFixationProtectionStrategy();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserBuilder users = User.builder().passwordEncoder(passwordEncoder::encode);
        UserDetails user = users
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
