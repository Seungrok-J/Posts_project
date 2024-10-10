package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    List<VerificationToken> findByEmailOrderByExpiryDateDesc(String email);

    VerificationToken findByEmail(String email);

}
