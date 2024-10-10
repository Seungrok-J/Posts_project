package org.boot.post_springboot.demo.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.VerificationToken;
import org.boot.post_springboot.demo.dto.MailDTO;
import org.boot.post_springboot.demo.dto.VerificationRequestDTO;
import org.boot.post_springboot.demo.repository.VerificationTokenRepository;
import org.boot.post_springboot.demo.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendSimpleMessage(mailDTO.getEmail());
        String token = emailService.createVerificationToken(mailDTO.getEmail(), authCode);  // 토큰 생성 및 저장
        return ResponseEntity.ok(authCode);
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<String> verifyToken(@RequestBody VerificationRequestDTO requestDto) {
        List<VerificationToken> tokens = verificationTokenRepository.findByEmailOrderByExpiryDateDesc(requestDto.getEmail());
        if (tokens.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token found");
        }

        VerificationToken latestToken = tokens.get(0); // 가장 최근 토큰
        if (latestToken.getAuthCode().equals(requestDto.getToken()) &&
                latestToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            return ResponseEntity.ok("Token verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }

}