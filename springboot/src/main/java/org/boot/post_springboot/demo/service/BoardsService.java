package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

// BoardsService.java
public interface BoardsService {
    List<Boards> getAllBoards();
    List<Boards> getAllActiveBoards();
    Boards getBoardById(UUID boardId);
    Boards saveBoard(BoardDTO boardDTO);
    Boards updateBoard(UUID boardId, BoardDTO boardDTO);
    void deleteBoard(UUID boardId);
    List<Boards> findAllByUser(UUID userId);
}