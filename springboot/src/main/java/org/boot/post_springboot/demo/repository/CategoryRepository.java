package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Categories, UUID> {
    Categories findByCateId(UUID CateId);

}