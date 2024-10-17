package org.boot.post_springboot.demo.security;

import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserEmail(username);

        // Optional을 사용하여 null 체크와 예외 처리를 간결하게 처리
        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + username));

        // 역할이 단일 문자열일 경우 배열로 변환
        String[] roles = new String[]{user.getRole()};

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserEmail())
                .password(user.getPassword())
                .roles(roles) // 여러 역할을 지원
                .accountExpired(false) // 필요한 경우 설정
                .accountLocked(false) // 필요한 경우 설정
                .credentialsExpired(false) // 필요한 경우 설정
                .disabled(false) // 필요한 경우 설정
                .build();
    }
}
