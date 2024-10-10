package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickName(nickname);
    }

    public Optional<User> findByUserEmail(String userEmail) {
        Optional<User> userOpt = userRepository.findByUserEmail(userEmail);
        return userOpt;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
