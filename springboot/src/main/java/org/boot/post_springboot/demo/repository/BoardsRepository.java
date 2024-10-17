package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardsRepository extends JpaRepository<Boards,Long>{


    List<Boards> findAllByUser_UserId(Long userId);

    Categories findByCategory_CateId(Long categoryId);

    List<Boards> findByIsDeletedFalse(); // 삭제되지 않은 게시물만 조회
}