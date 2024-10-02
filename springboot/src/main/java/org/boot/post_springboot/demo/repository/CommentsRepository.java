package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long>{
    
}
