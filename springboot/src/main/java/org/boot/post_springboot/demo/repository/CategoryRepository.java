package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
