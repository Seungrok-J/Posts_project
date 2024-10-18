package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface BoardsRepository extends JpaRepository<Boards, UUID>{


    List<Boards> findAllByUser_UserId(UUID userId);

    Categories findByCategory_CateId(UUID categoryId);

    // 삭제되지 않은 게시글 조회
    List<Boards> findByIsDeleted(int isDeleted);


    @Modifying
    @Query("UPDATE Boards b SET b.isDeleted = 1 WHERE b.boardId = :boardId")
    void updateIsDeleted(@Param("boardId") UUID boardId); // 사용자가 글을 삭제할 시 상태를 숫자 1로 바꾸어 보이지 않게 함.

}