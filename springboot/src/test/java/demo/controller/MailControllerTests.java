package demo.controller;


import org.boot.post_springboot.demo.PostSpringbootApplication;
import org.boot.post_springboot.demo.controller.MailController;
import org.boot.post_springboot.demo.domain.VerificationToken;
import org.boot.post_springboot.demo.dto.MailDTO;
import org.boot.post_springboot.demo.dto.VerificationRequestDTO;
import org.boot.post_springboot.demo.repository.VerificationTokenRepository;
import org.boot.post_springboot.demo.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = PostSpringbootApplication.class)  // 메인 애플리케이션 클래스 지정
@AutoConfigureMockMvc  // MockMvc 자동 구성
public class MailControllerTests {

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    public void testEmailCheck() throws Exception {
        // Setup
        String email = "user@example.com";
        when(emailService.sendSimpleMessage(email)).thenReturn("123456");

        // Execute & Verify
        mockMvc.perform(MockMvcRequestBuilders.post("/emailCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("123456"));
    }

    @Test
    public void testVerifyToken() throws Exception {
        // 예시: 목 객체 설정
        when(verificationTokenRepository.findByEmail(anyString())).thenReturn(new VerificationToken(1L, "user@example.com", "123456", LocalDateTime.now().plusMinutes(30)));

        mockMvc.perform(MockMvcRequestBuilders.post("/verifyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"token\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Token verified successfully"));
    }
}
