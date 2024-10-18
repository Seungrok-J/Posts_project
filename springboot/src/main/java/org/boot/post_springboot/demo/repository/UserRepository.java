package org.boot.post_springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.boot.post_springboot.demo.domain.User;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByNickName(String nickName);
    Optional<User> findByUserEmail(String userEmail);
    User findByUserId(UUID userId);
    Optional<User> findByUserName(String userName);
}