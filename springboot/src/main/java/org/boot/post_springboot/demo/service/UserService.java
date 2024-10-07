package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 회원가입 기능
    public User registerUser(User user) throws Exception {
        if (userRepository.existsByNickName(user.getNickName())) {
            throw new Exception("사용중인 닉네임 입니다.");
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new Exception("사용중인 이메일 입니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

}
