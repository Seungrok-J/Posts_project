package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Boards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoardsRepository extends JpaRepository<Boards,Long>{

    List<Boards> findAllByCategory_CateId(Long cateId);

    List<Boards> findAllByUser_UserId(Long userId);
}
