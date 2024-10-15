package org.boot.post_springboot.demo.controller;


import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.BoardsService;
import org.boot.post_springboot.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BoardsRepository boardsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // 전체 카테고리 보기
    @GetMapping("/categories")
    public List<Categories> getCategory() {
        return categoryService.getAllCategories();
    }


    // 유저 별 글 보기 기능(마이페이지)
    @GetMapping("/{userId}/boardList")
    public List<Boards> getBoardsByUserId(@PathVariable("userId") Long userId) {
        return boardsService.findAllByUser(userId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Boards>> getAllBoards() {
        return ResponseEntity.ok(boardsService.getAllBoards());
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Boards> getBoardById(@PathVariable Long id) {
        return ResponseEntity.of(boardsService.getBoardById(id));
    }


    @PostMapping("/save")
    public ResponseEntity<Boards> saveBoard(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // 로그인한 유저의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 유저의 username을 가져옴

        // 유저 정보를 데이터베이스에서 찾기 위한 서비스 호출
        User user = userRepository.findByUserName(username); // UserService에서 유저를 찾는 메서드 호출

        // 카테고리 정보를 가져옵니다.
        Categories category = categoryRepository.findByCateId(categoryId); // CategoryService에서 카테고리를 찾는 메서드 호출

        // 게시글 객체 생성
        Boards board = Boards.builder()
                .title(title)
                .content(content)
                .category(category)
                .user(user) // 로그인한 유저 정보 설정
                .isDeleted(false) // 기본적으로 삭제되지 않은 상태
                .count(0L) // 조회수 초기화
                .build();

        // 파일이 있는 경우 처리
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "/uploads/" + fileName; // 파일 경로 설정 (예시)

            try {
                // 파일을 지정된 경로에 저장 (예: 서버의 로컬 파일 시스템에)
                file.transferTo(new File(filePath));
                board.setFileName(fileName);
                board.setFilePath(filePath);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
            }
        }

        // 게시글 저장
        Boards savedBoard = boardsRepository.save(board); // 게시글 저장

        return ResponseEntity.ok(savedBoard); // 저장된 게시글 반환
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boards> updateBoard(@PathVariable Long id, @RequestBody Boards board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(boardsService.updateBoard(id, board));
        }
        return ResponseEntity.status(403).build(); // 인증되지 않은 사용자는 Forbidden 응답
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            boardsService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(403).build(); // 인증되지 않은 사용자는 Forbidden 응답
    }

}
