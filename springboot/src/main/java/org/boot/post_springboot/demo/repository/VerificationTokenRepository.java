package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByEmail(String email);

}
