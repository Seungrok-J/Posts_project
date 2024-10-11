package org.boot.post_springboot.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Verification_Token")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

    private String authCode;

    private LocalDateTime expiryDate;

    // 기본 생성자
    public VerificationToken() {
    }

    // 파라미터를 받는 생성자
    public VerificationToken(Long id, String email, String token, String authCode, LocalDateTime expiryDate) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.authCode = authCode;
        this.expiryDate = expiryDate;

    }

}
