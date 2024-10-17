package org.boot.post_springboot.demo.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.domain.UserRole;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        try {
            log.info("로그인 시도: 이메일={}", loginDto.getUserEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByUserEmail(loginDto.getUserEmail());

            if (user == null) {
                log.error("사용자 정보를 찾을 수 없음: 이메일={}", loginDto.getUserEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 정보를 찾을 수 없습니다.");
            }

            session.setAttribute("USER", user);
            log.info("로그인 성공: 세션 ID={}, 사용자 ID={}", session.getId(), user.getUserId());

            UserDTO responseDTO = new UserDTO(user);
            responseDTO.setSessionId(session.getId());

            return ResponseEntity.ok(responseDTO);
        } catch (AuthenticationException e) {
            log.error("로그인 실패: 이메일={}, 에러={}", loginDto.getUserEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 정확하지 않습니다.");
        } catch (Exception e) {
            log.error("서버 에러: 이메일={}, 에러={}", loginDto.getUserEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다.");
        }
    }


//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session) {
//        session.invalidate();
//        SecurityContextHolder.clearContext();
//        return ResponseEntity.ok().body("Logout successful");
//    }
//
//    // 세션확인
//    @GetMapping("/user")
//    public ResponseEntity<?> getCurrentUser(HttpSession session) {
//        User user = (User) session.getAttribute("USER");
//        if (user != null) {
//            return ResponseEntity.ok(new UserDTO(user));
//        }
//        return ResponseEntity.status(401).body("Not logged in");
//    }


}
