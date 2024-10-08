package org.boot.post_springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import org.boot.post_springboot.demo.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickName(String nickName);
    boolean existsByUserEmail(String userEmail);
}
