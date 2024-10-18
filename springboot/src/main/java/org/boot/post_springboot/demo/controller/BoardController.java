package org.boot.post_springboot.demo.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.dto.CategoryDTO;
import org.boot.post_springboot.demo.dto.UserDTO;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.BoardsService;
import org.boot.post_springboot.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    // 전체 카테고리 보기
    @GetMapping("/categories")
    public List<Categories> getCategory() {
        return categoryService.getAllCategories();
    }


    // 유저 별 글 보기 기능(마이페이지)
    @GetMapping("/{userId}/boardList")
    public List<Boards> getBoardsByUserId(@PathVariable("userId") UUID userId) {
        return boardsService.findAllByUser(userId);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Boards>> getAllBoards() {
        return ResponseEntity.ok(boardsService.getAllActiveBoards());
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<Boards> getBoardById(@PathVariable UUID boardId) {
        return ResponseEntity.ok(boardsService.getBoardById(boardId));
    }


    @Value("${spring.servlet.multipart.location}")
    private String uploadDir; // uploadDir 값을 주입받음

    @PostMapping("/save")
    public ResponseEntity<Boards> saveBoard(
            @RequestParam("userId") UUID userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // 게시글 DTO 생성
        BoardDTO boardDTO = BoardDTO.builder()
                .title(title)
                .content(content)
                .userId(userId) // 작성자 정보 설정
                .cateName(categoryName) // 카테고리 ID 설정
                .build();

        // 업로드 디렉토리 생성
        File uploadDirFile = new File(uploadDir); // uploadDir 변수를 사용
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs(); // 경로가 없다면 생성
        }

        // 파일 저장 로직
        if (file != null && !file.isEmpty()) {
            File serverFile = new File(uploadDirFile, file.getOriginalFilename()); // 수정된 부분
            try {
                file.transferTo(serverFile); // 실제 파일 저장
                // 파일 경로와 이름을 DTO에 저장
                boardDTO.setFilePath(serverFile.getAbsolutePath()); // 절대 경로 저장
                boardDTO.setFileName(file.getOriginalFilename()); // 파일 이름 저장
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 에러 발생 시 처리
            }
        }

        // 게시글 저장
        Boards savedBoard = boardsService.saveBoard(
                boardDTO,
                userRepository.findByUserId(userId),
                categoryRepository.findByCateName(categoryName)
        ); // 게시글 저장

        log.debug("Board saved successfully: {}", savedBoard);
        return ResponseEntity.ok(savedBoard); // 저장된 게시글 반환
    }

    @PutMapping("/update/{boardId}")
    public ResponseEntity<Boards> updateBoard(
            @PathVariable("boardId") UUID boardId,
            @RequestParam("userId") UUID userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // 기존 게시글 가져오기
        Boards existingBoard = boardsService.getBoardById(boardId);
        if (existingBoard == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 게시글 DTO 업데이트
        BoardDTO boardDTO = BoardDTO.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .cateName(categoryName)
                .build();

        // 파일 처리
        String filePath = handleFileUpload(file);
        if (filePath != null) {
            boardDTO.setFilePath(filePath);
            boardDTO.setFileName(file.getOriginalFilename());
        }

        // 게시글 업데이트
        Boards updatedBoard = boardsService.updateBoard(boardId, boardDTO);

        log.debug("Board updated successfully: {}", updatedBoard);
        return ResponseEntity.ok(updatedBoard);
    }

    private String handleFileUpload(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                // 파일 저장 로직
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                File serverFile = new File(uploadDirFile, file.getOriginalFilename());
                file.transferTo(serverFile);
                return serverFile.getAbsolutePath();
            } catch (IOException e) {
                log.error("File upload failed", e);
            }
        }
        return null;
    }



    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") UUID boardId) {
        try {
            Boards existingBoard = boardsService.getBoardById(boardId);
            if (existingBoard == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found 응답
            }

            // isDeleted 플래그를 true로 설정
            existingBoard.setIsDeleted(1L);
            boardsService.saveBoard(existingBoard); // 업데이트된 엔티티 저장

            return ResponseEntity.noContent().build(); // 204 No Content 응답
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found 응답
        }
    }
}

