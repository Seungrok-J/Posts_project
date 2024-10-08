package org.boot.post_springboot.demo.dto;


public class VerificationRequestDTO {
    private String email;
    private String token;

    // 기본 생성자
    public VerificationRequestDTO() {
    }

    // 매개변수가 있는 생성자
    public VerificationRequestDTO(String email, String token) {
        this.email = email;
        this.token = token;
    }

    // email 필드의 getter
    public String getEmail() {
        return email;
    }

    // email 필드의 setter
    public void setEmail(String email) {
        this.email = email;
    }

    // token 필드의 getter
    public String getToken() {
        return token;
    }

    // token 필드의 setter
    public void setToken(String token) {
        this.token = token;
    }
}
