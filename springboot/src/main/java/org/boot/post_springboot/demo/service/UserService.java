package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickName(nickname);
    }



    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
    }

    @Cacheable(value = "userCache", key = "#userEmail", unless = "#result == null")
    public Optional<User> findByUserEmailOptional(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

}
