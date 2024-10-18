package org.boot.post_springboot.demo.dto;

import lombok.*;
import org.boot.post_springboot.demo.domain.Boards;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private UUID boardId; // 게시글 ID (수정 시 필요)
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로
    private UUID userId; // 작성자 정보
    private Long isDeleted; // 삭제 상태
//    private UUID cateId; // 카테고리 ID (카테고리 정보)
    private String cateName; // 카테고리 이름 (카테고리 이름)


}