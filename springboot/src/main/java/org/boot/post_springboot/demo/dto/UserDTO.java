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

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.nickName = user.getNickName();
        this.password = user.getPassword();
        this.role = user.getRole(); // Assuming roles are handled safely elsewhere
    }

}
