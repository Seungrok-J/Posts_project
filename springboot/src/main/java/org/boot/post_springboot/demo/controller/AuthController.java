package org.boot.post_springboot.demo.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.domain.UserRole;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private  UserRepository userRepository;


    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
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
            // 먼저 사용자가 존재하는지 빠르게 확인
            Optional<User> userOptional = userRepository.findByUserEmail(loginDto.getUserEmail());
            if (userOptional.isEmpty()) {
                log.warn("로그인 실패: 존재하지 않는 이메일={}", loginDto.getUserEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Login failed: Invalid email or password");
            }

            // 사용자가 존재하면 인증 진행
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userOptional.get();

            // 세션 관리
            session.setAttribute("USER", user);
            log.info("로그인 성공: 세션 ID={}", session.getId());

            UserDTO responseDTO = new UserDTO(user);
            responseDTO.setSessionId(session.getId());
            return ResponseEntity.ok(responseDTO);

        } catch (AuthenticationException e) {
            log.error("로그인 실패: 이메일={}, 에러메시지={}", loginDto.getUserEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: Invalid email or password");
        }
    }





}
