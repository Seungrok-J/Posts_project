package org.boot.post_springboot.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.dto.BoardDTO;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.BoardsService;
import org.boot.post_springboot.demo.service.CategoryService;
import org.boot.post_springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BoardsService boardsService;
    private final CategoryService categoryService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    @GetMapping("/categories")
    public List<Categories> getCategory() {
        return categoryService.getAllCategories();
    }

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

    @PostMapping("/save")
    public ResponseEntity<Boards> saveBoard(
            @RequestParam("userId") UUID userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        BoardDTO boardDTO = createBoardDTO(title, content, userId, categoryName, file);
        Boards savedBoard = boardsService.saveBoard(boardDTO);
        log.debug("Board saved successfully: {}", savedBoard);
        return ResponseEntity.ok(savedBoard);
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
        BoardDTO boardDTO = createBoardDTO(title, content, userId, categoryName, file);
        Boards updatedBoard = boardsService.updateBoard(boardId, boardDTO);
        log.debug("Board updated successfully: {}", updatedBoard);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") UUID boardId) {
        boardsService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }

    private BoardDTO createBoardDTO(String title, String content, UUID userId, String categoryName, MultipartFile file) {
        BoardDTO boardDTO = BoardDTO.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .cateName(categoryName)
                .build();

        if (file != null && !file.isEmpty()) {
            String filePath = handleFileUpload(file);
            if (filePath != null) {
                boardDTO.setFilePath(filePath);
                boardDTO.setFileName(file.getOriginalFilename());
            }
        }

        return boardDTO;
    }

    private String handleFileUpload(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
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
}