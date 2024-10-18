package org.boot.post_springboot.demo.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID cateId; // 카테고리 ID
    private String cateName; // 카테고리 이름

}
