package org.boot.post_springboot.demo.controller;


import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.service.BoardService;
import org.boot.post_springboot.demo.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
public class BoardController {

    //    private final FileService fileService; // 파일서비스 의존성 주입
    private final BoardService boardService;
    //
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존재하는 게시글 수
    // 글 작성기능

    // 글 삭제기능

    // 글 수정기능

    // 글 상세보기 기능


    // 게시판 목록보기 기능
    @GetMapping("/list")
    public List<Boards> boardList() {
        // boardList를 호출하여 게시글 목록 반환

        return boardService.boardList();
    }

    // 카테고리 별 글 보기 기능

    //유저 별 글 보기 기능


}
