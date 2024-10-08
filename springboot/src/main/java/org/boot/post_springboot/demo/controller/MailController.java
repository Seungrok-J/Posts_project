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
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @PostMapping("/emailCheck")
    public String emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException , UnsupportedEncodingException {
        String authCode = emailService.sendSimpleMessage(mailDTO.getEmail());
        return authCode;
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<String> verifyToken(@RequestBody VerificationRequestDTO requestDto) {
        VerificationToken token = verificationTokenRepository.findByEmail(requestDto.getEmail());
        if (token != null && token.getToken().equals(requestDto.getToken()) &&
                token.getExpiryDate().isAfter(LocalDateTime.now())) {
            return ResponseEntity.ok("Token verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }
}
