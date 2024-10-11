package org.boot.post_springboot.demo.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.domain.VerificationToken;
import org.boot.post_springboot.demo.dto.MailDTO;
import org.boot.post_springboot.demo.dto.VerificationRequestDTO;
import org.boot.post_springboot.demo.repository.VerificationTokenRepository;
import org.boot.post_springboot.demo.service.EmailService;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {
    private final EmailService emailService;
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException {
        Optional<User> existingUser = emailService.findByUserEmail(mailDTO.getEmail());
        log.info("유저이메일: {}",existingUser.isPresent());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        String authCode = emailService.sendSimpleMessage(mailDTO.getEmail());
        emailService.createVerificationToken(mailDTO.getEmail(), authCode);
        return ResponseEntity.ok(authCode);
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<String> verifyToken(@RequestBody VerificationRequestDTO requestDto) {
        List<VerificationToken> authCodes = verificationTokenRepository.findByEmailOrderByExpiryDateDesc(requestDto.getEmail());
        if (authCodes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token found");
        }

        VerificationToken latestToken = authCodes.get(0); // 가장 최근 토큰
        if (latestToken.getAuthCode().equals(requestDto.getAuthCode()) &&
                latestToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            return ResponseEntity.ok("Token verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }

}