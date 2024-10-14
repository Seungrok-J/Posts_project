package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardsService {

    @Autowired
    private BoardsRepository boardsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 게시물 전체 조회(게시물 목록보기)
    public List<Boards> getAllBoards() {
        return boardsRepository.findAll();
    }

    // 특정 게시물 조회(검색기능)
    public Boards getBoardById(Long boardId) {
        // 게시물 찾기
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        // 조회수 증가
        board.setCount(board.getCount() + 1);

        // 변경된 조회수 저장
        boardsRepository.save(board);

        return board;
    }

    // 게시물 작성 기능 (인증된 사용자만 작성 가능)
    public Boards saveBoard(Boards board) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증된 사용자만 게시물을 작성할 수 있습니다.");
        }

        // 현재 로그인된 사용자 정보
        String userid = authentication.getName();
        User user = userRepository.findByUserName(userid)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 작성자를 게시물에 설정
        board.setUser(user);

        return boardsRepository.save(board);
    }

    // 게시물 수정 기능 (작성자만 수정 가능)
    public Boards updateBoard(Long boardId, Boards updatedBoard) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증된 사용자만 게시물을 수정할 수 있습니다.");
        }

        // 게시물과 작성자 정보 가져오기
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        String username = authentication.getName();

        // 게시물 작성자와 로그인한 사용자 비교
        if (!board.getUser().getUserName().equals(username)) {
            throw new SecurityException("작성자만 게시물을 수정할 수 있습니다.");
        }

        // 게시물 내용 업데이트
        board.setTitle(updatedBoard.getTitle());
        board.setContent(updatedBoard.getContent());
        board.setCategory(updatedBoard.getCategory());

        return boardsRepository.save(board);
    }

    // 게시물 삭제 기능 (작성자만 삭제 가능)
    public void deleteBoard(Long boardId) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증된 사용자만 게시물을 삭제할 수 있습니다.");
        }

        // 게시물과 작성자 정보 가져오기
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        String username = authentication.getName();

        // 작성자가 동일한지 확인
        if (!board.getUser().getUserName().equals(username)) {
            throw new SecurityException("작성자만 게시물을 삭제할 수 있습니다.");
        }

        // 게시물 삭제
        boardsRepository.delete(board);
    }

    public List<Boards> findAllByCategory(Long cateId) {
        return boardsRepository.findByCategory_CateId(cateId); // 이 메서드는 BoardsRepository에서 정의해야 합니다.
    }
//    내가 쓴 글 보기 기능
    public List<Boards> findAllByUser(Long userId) {
        return boardsRepository.findAllByUser_UserId(userId);
    }

}
