package org.boot.post_springboot.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")

public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @CreationTimestamp

    private LocalDateTime createdAt;

    @UpdateTimestamp

    private LocalDateTime updatedAt;

    private String filePath;

    // 조회수
    private Long count;

    // 삭제한 시간
    @UpdateTimestamp
    private LocalDateTime deletedAt;

    // 글 삭제 여부: 1인 경우 삭제되어 안보이게
    private Boolean isDeleted;

    private String user_id;
    // User 테이블과 1대 다 관계

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}