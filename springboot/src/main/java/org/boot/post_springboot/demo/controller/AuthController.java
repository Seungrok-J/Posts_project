package org.boot.post_springboot.demo.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.domain.UserRole;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;


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
        log.info("로그인 시도 : {}", loginDto.getUserEmail());
        log.info("비밀번호 : {}", loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByUserEmail(loginDto.getUserEmail());
        session.setAttribute("USER", user);

        log.info("로그인 성공. 세션 ID: {}", session.getId());
        log.info("세션에 저장된 사용자 정보: {}", user);
        UserDTO responseDTO = new UserDTO(user);
        responseDTO.setSessionId(session.getId());
        log.info("DTO: {}", responseDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Logout successful");
    }

    // 세션확인
    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("USER");
        if (user != null) {
            return ResponseEntity.ok(new UserDTO(user));
        }
        return ResponseEntity.status(401).body("Not logged in");
    }


}
