package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.dto.CategoryDTO;
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
        category = categoryRepository.findByCateId(category.getCateId());

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

    // 게시물 수정 기능
    public Boards updateBoard(UUID boardId, BoardDTO boardDTO, String username) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        // 작성자와 로그인된 사용자 비교
        if (!board.getUser().getUserName().equals(username)) {
            throw new SecurityException("작성자만 게시물을 수정할 수 있습니다.");
        }

        // 게시물 내용 업데이트
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setCategory(categoryRepository.findById(boardDTO.getCateId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다.")));

        return boardsRepository.save(board); // 변경된 게시글 저장
    }

    public void deleteBoard(UUID boardId, @AuthenticationPrincipal User user) {
        // 게시글 조회
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 현재 로그인한 사용자의 ID와 게시글 작성자의 ID 비교
        if (!board.getUser().getUserId().equals(user.getUserId())) {
            logger.error("게시물 삭제 실패: 권한이 없는 사용자입니다.");
            throw new SecurityException("삭제 권한이 없습니다."); // 권한이 없을 경우 예외 발생
        }

        // 게시글 삭제
        boardsRepository.delete(board);
        logger.info("게시물이 성공적으로 삭제되었습니다. 게시물 ID: " + boardId);
    }

    public Categories findAllByCategory(UUID cateId) {

        return boardsRepository.findByCategory_CateId(cateId); // 이 메서드는 BoardsRepository에서 정의해야 합니다.
    }

    //    내가 쓴 글 보기 기능
    public List<Boards> findAllByUser(UUID userId) {
        return boardsRepository.findAllByUser_UserId(userId);
    }

}