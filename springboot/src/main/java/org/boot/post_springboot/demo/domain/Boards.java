package org.boot.post_springboot.demo.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Boards")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private UUID boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String fileName;

    private String filePath;

    // 조회수
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long count = 0L;

    // 삭제한 시간
    @UpdateTimestamp
    private LocalDateTime deletedAt;

    // 글 삭제 여부: 1인 경우 삭제되어 안보이게
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long isDeleted =0L;

    // User 테이블과 1대 다 관계

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Categories category;
}