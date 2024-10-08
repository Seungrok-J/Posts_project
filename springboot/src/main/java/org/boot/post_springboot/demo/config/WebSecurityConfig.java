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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/home", "/public/**","/emailCheck","/verifyToken").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .sessionManagement(management -> management
                        .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?invalid")
                        .maximumSessions(1)
                        .expiredUrl("/login?expired")
                        .maxSessionsPreventsLogin(true));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // 모든 도메인에서의 접근을 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용된 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 크레덴셜 허용
        configuration.setMaxAge(3600L); // 사전 요청(precache) 결과를 3600초 동안 캐시

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }

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
