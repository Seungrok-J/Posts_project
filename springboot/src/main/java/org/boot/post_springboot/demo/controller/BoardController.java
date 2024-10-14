package org.boot.post_springboot.demo.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.service.BoardsService;
import org.boot.post_springboot.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private CategoryService categoryService;

//    @Autowired

    //     글 작성기능
    @PostMapping("/save")
    public ResponseEntity<Boards> save(@RequestBody Boards boards) {
        try {
            // 게시물 저장
            Boards savedBoard = boardsService.saveBoard(boards);
            return ResponseEntity.ok(savedBoard); // 작성된 게시물을 반환
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 인증되지 않은 경우 401 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 서버 오류 500 응답
        }
    }


    // 글 삭제기능
    @GetMapping("/{boardId}/delete") // 현재는 값을 직접 줘서 get으로 진행 추후 프론트 진행 시, DeleteMapping로 변경
    public ResponseEntity<Boards> delete(@PathVariable Long boardId) {
        try {
            // 게시물 수정
            boardsService.deleteBoard(boardId);
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 권한 없음 401 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 게시물 없음 404 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 서버 오류 500 응답
        }

    }


    // 글 수정기능
    @GetMapping("/{boardId}/update")// 현재는 값을 직접 줘서 get으로 진행 추후 프론트 진행 시, PutMapping로 변경
    public ResponseEntity<Boards> update(@PathVariable Long boardId, @RequestBody Boards boards) {
        try {
            // 게시물 수정
            Boards updatedBoard = boardsService.updateBoard(boardId, boards);
            return ResponseEntity.ok(updatedBoard); // 수정된 게시물 반환
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 권한 없음 401 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 게시물 없음 404 응답
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 서버 오류 500 응답
        }
    }

    // 글 상세보기 기능
    @GetMapping("/{boardId}/detail")
    public Boards getBoard(@PathVariable Long boardId) {
        Boards board = boardsService.getBoardById(boardId);
        return board;
    }

    // 게시판 목록보기 기능
    // 및 카테고리 all의 내용을 의미 -> 카테고리만 불러 오는 것이 아님, 쿼리문이 다름
    @GetMapping("/list")
    public List<Boards> getAllBoards() {
        return boardsService.getAllBoards();
    }

    // 카테고리 별 글 보기 기능
    @GetMapping("/categories")
    public List<Categories> getCategory(Long cateId) {
        return categoryService.getAllCategories();
    }
    

    // 유저 별 글 보기 기능(마이페이지)
    @GetMapping("/{userId}/boardList")
    public List<Boards> getBoardsByUserId(@PathVariable("userId") Long userId) {
        return boardsService.findAllByUser(userId);
    }

}
