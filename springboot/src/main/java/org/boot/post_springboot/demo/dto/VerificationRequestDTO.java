package org.boot.post_springboot.demo.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationRequestDTO {
    private String email;
    private String authCode;

}
