package org.boot.post_springboot.demo.service;

import jakarta.persistence.EntityNotFoundException;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BoardsService {

    @Autowired
    private BoardsRepository boardsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(BoardsService.class);

    // 게시물 전체 조회(게시물 목록보기)
    public List<Boards> getAllBoards() {
        return boardsRepository.findAll();
    }

    public List<Boards> getAllActiveBoards() {
        return boardsRepository.findByIsDeleted(0); // isDeleted가 0인 게시글만 반환
    }


    // 특정 게시물 조회(검색기능)
    public Boards getBoardById(UUID boardId) {
        // 게시물 찾기
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        // 조회수 증가
        board.setCount(board.getCount() + 1);

        // 변경된 조회수 저장
        boardsRepository.save(board);

        return board;
    }

    //
    // 게시물 저장 기능
    public Boards saveBoard(BoardDTO boardDTO,
                            @AuthenticationPrincipal User user, Categories category) {
        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user = userRepository.findByUserId(user.getUserId());
        category = categoryRepository.findByCateName(category.getCateName());

        // 게시글 객체 생성
        Boards board = Boards.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .category(category)
                .user(user)
                .isDeleted(0L) // 기본적으로 삭제되지 않은 상태
                .count(0L) // 조회수 초기화
                .fileName(boardDTO.getFileName())
                .filePath(boardDTO.getFilePath())
                .build();

        return boardsRepository.save(board); // 게시글 저장
    }
    public Boards updateBoard(UUID boardId, BoardDTO boardDTO) {
        Boards existingBoard = getBoardById(boardId);
        if (existingBoard == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        // 게시글 정보 업데이트
        existingBoard.setTitle(boardDTO.getTitle());
        existingBoard.setContent(boardDTO.getContent());
        existingBoard.setUser(userRepository.findByUserId(boardDTO.getUserId()));
        existingBoard.setCategory(categoryRepository.findByCateName(boardDTO.getCateName()));
        existingBoard.setUpdatedAt(LocalDateTime.now());

        // 게시글 업데이트 저장
        return boardsRepository.save(existingBoard);
    }

    public void deleteBoard(UUID boardId) {
        Boards existingBoard = getBoardById(boardId);
        if (existingBoard == null) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
        }

        // 논리 삭제 수행
        existingBoard.setIsDeleted(1L);  // 1로 설정하여 삭제 상태로 변경
        boardsRepository.save(existingBoard);
    }

    public Categories findAllByCategory(UUID cateId) {

        return boardsRepository.findByCategory_CateId(cateId); // 이 메서드는 BoardsRepository에서 정의해야 합니다.
    }

    //    내가 쓴 글 보기 기능
    public List<Boards> findAllByUser(UUID userId) {
        return boardsRepository.findAllByUser_UserId(userId);
    }
    public void saveBoard(Boards board) {
        boardsRepository.save(board); // isDeleted 상태와 함께 업데이트된 엔티티 저장
    }


}