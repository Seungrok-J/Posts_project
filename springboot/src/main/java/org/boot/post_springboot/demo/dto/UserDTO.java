package org.boot.post_springboot.demo.dto;

import org.boot.post_springboot.demo.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userEmail;
    private String password;
    private String passwordCheck;
    private String userName;
    private String nickName;

    public static UserDto of(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUserName(user.getUserName());
        userDto.setNickName(user.getNickName());
        return userDto;
    }

}
