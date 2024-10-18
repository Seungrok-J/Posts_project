package org.boot.post_springboot.demo.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.domain.UserRole;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.response.ErrorResponse;
import org.boot.post_springboot.demo.security.RSAKeyGenerator;
import org.boot.post_springboot.demo.service.AuthService;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private RSAKeyGenerator rsaKeyGenerator;
    @Autowired
    private AuthService authService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // 공개키를 제공하는 엔드포인트
    @GetMapping("/public-key")
    public ResponseEntity<String> getPublicKey() {
        PublicKey publicKey = rsaKeyGenerator.getPublicKey();
        String encodedKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        return ResponseEntity.ok(encodedKey);
    }

    @GetMapping("/isExist/{nickName}")
    public ResponseEntity<Boolean> checkNickName(@PathVariable String nickName) {
        return ResponseEntity.ok(userService.checkNickname(nickName));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        User newUser = User.builder()
                .userName(userDTO.getUserName())
                .nickName(userDTO.getNickName())
                .userEmail(userDTO.getUserEmail())
                .password(userDTO.getPassword()) // Ensure this is securely managed/encoded within the service
                .role(String.valueOf(UserRole.USER)) // Example role setting; adjust based on business logic
                .build();
        User savedUser = userService.saveUser(newUser);
        UserDTO responseDTO = new UserDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginDto, HttpSession session) {
        try {
            // 비밀번호 복호화
            String decryptedPassword = authService.decryptPassword(loginDto.getPassword());

            // 복호화된 비밀번호로 인증 진행
            // 1. 빠른 자격 증명 검사
            boolean isValid = userService.validateUserCredentials(loginDto.getUserEmail(),decryptedPassword);
            if (!isValid) {
                log.warn("로그인 실패: 잘못된 자격 증명 - 이메일={}", loginDto.getUserEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Invalid email or password"));
            }

            // 2. 사용자 정보 가져오기
            Optional<User> userOptional = userService.findByUserEmailOptional(loginDto.getUserEmail());
            if (userOptional.isEmpty()) {
                log.warn("사용자 조회 실패: 이메일={}", loginDto.getUserEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("User not found"));
            }
            User user = userOptional.get();

            // 3. Spring Security 인증 처리
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUserEmail(),
                    null, // 이미 검증되었으므로 credentials는 null
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 4. 세션 처리
            session.setAttribute("USER", user);
            log.info("로그인 성공: 유저={}, 세션ID={}", user.getUserEmail(), session.getId());

            // 5. 응답 생성
            UserDTO responseDTO = new UserDTO(user);
            responseDTO.setSessionId(session.getId());
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생: 이메일={}, 에러메시지={}", loginDto.getUserEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Login processing error"));
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
        return ResponseEntity.ok().body("Successfully logged out");
    }
}





