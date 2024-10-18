package org.boot.post_springboot.demo.service.implement;

import jakarta.persistence.EntityNotFoundException;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.BoardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class BoardsServiceImpl implements BoardsService {
    private static final Logger logger = LoggerFactory.getLogger(BoardsServiceImpl.class);

    @Autowired
    private BoardsRepository boardsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Boards> getAllBoards() {
        return boardsRepository.findAll();
    }

    @Override
    public List<Boards> getAllActiveBoards() {
        return boardsRepository.findByIsDeleted(0);
    }

    @Override
    public Boards getBoardById(UUID boardId) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        board.setCount(board.getCount() + 1);
        return boardsRepository.save(board);
    }

    @Override
    public Boards saveBoard(BoardDTO boardDTO) {
        User user = userRepository.findByUserId(boardDTO.getUserId());
        Categories category = categoryRepository.findByCateName(boardDTO.getCateName());

        Boards board = Boards.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .category(category)
                .user(user)
                .isDeleted(0L)
                .count(0L)
                .fileName(boardDTO.getFileName())
                .filePath(boardDTO.getFilePath())
                .build();

        return boardsRepository.save(board);
    }

    @Override
    public Boards updateBoard(UUID boardId, BoardDTO boardDTO) {
        Boards existingBoard = getBoardById(boardId);
        if (existingBoard == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        existingBoard.setTitle(boardDTO.getTitle());
        existingBoard.setContent(boardDTO.getContent());
        existingBoard.setUser(userRepository.findByUserId(boardDTO.getUserId()));
        existingBoard.setCategory(categoryRepository.findByCateName(boardDTO.getCateName()));
        existingBoard.setUpdatedAt(LocalDateTime.now());

        return boardsRepository.save(existingBoard);
    }

    @Override
    public void deleteBoard(UUID boardId) {
        Boards existingBoard = getBoardById(boardId);
        if (existingBoard == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        existingBoard.setIsDeleted(1L);
        boardsRepository.save(existingBoard);
    }

    @Override
    public List<Boards> findAllByUser(UUID userId) {
        return boardsRepository.findAllByUser_UserId(userId);
    }
}