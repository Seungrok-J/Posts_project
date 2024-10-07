package org.boot.post_springboot.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentsId;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Boards boards;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comments parentComment; // 부모 댓글 (null이면 최상위 댓글)

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comments> childComments; // 대댓글 리스트

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int likeCount;

    // 댓글 작성자의 닉네임을 가져오는 메서드
    public String getNickName() {
        return this.user.getNickName();
    }

    // 대댓글 여부 확인 메서드
    public boolean isReply() {
        return this.parentComment != null; // parentComment가 null이 아니면 대댓글
    }

}
