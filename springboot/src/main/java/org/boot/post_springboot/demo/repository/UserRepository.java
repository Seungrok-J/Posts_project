package org.boot.post_springboot.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.boot.post_springboot.demo.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(String userName);

    boolean exexistsByNickName(String nickName);
    boolean exexistsByUserEmail(String userEmail);

}
