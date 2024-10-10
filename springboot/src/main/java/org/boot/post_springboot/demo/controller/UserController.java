package org.boot.post_springboot.demo.controller;


import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/isExist/{nickName}")
    public ResponseEntity<Boolean> checkNickName(@PathVariable String nickName) {
        return ResponseEntity.ok(userService.checkNickname(nickName));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        User newUser = User.builder()
                .userName(userDTO.getUserName())
                .nickName(userDTO.getNickName())
                .userEmail(userDTO.getUserEmail())
                .password(userDTO.getPassword()) // Ensure this is securely managed/encoded within the service
                .role("DEFAULT_ROLE") // Example role setting; adjust based on business logic
                .build();
        User savedUser = userService.saveUser(newUser);
        UserDTO responseDTO = new UserDTO(savedUser);
        return ResponseEntity.ok(responseDTO);
    }


}
