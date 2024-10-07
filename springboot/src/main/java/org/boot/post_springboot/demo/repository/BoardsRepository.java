package org.boot.post_springboot.demo.repository;

import org.boot.post_springboot.demo.domain.Boards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardsRepository extends JpaRepository<Boards,Long>{



}
