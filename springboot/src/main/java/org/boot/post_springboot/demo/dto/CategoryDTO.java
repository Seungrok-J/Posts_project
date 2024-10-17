package org.boot.post_springboot.demo.dto;

import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long cateId; // 카테고리 ID
    private String cateName; // 카테고리 이름

}
