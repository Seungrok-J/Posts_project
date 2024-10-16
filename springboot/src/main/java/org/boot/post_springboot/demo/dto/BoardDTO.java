package org.boot.post_springboot.demo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long boardId; // 게시글 ID (수정 시 필요)
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로
    private Long userId; // 작성자 ID (사용자 정보)
    private Long categoryId; // 카테고리 ID (카테고리 정보)

}
