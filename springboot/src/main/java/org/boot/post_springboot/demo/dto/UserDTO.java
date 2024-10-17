package org.boot.post_springboot.demo.dto;

import lombok.*;
import org.boot.post_springboot.demo.domain.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String userEmail;
    private String userName;
    private String nickName;
    private String password;
    private String role;
    private String sessionId;
//    private String token; // JWT 토큰을 위한 필드 추가 (은지 수정)

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.nickName = user.getNickName();
        this.password = user.getPassword();
        this.role = user.getRole(); // Assuming roles are handled safely elsewhere
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", role='" + role + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }


}
