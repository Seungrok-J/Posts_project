package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.Comments;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.CommentsRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;
    // 댓글 객체를 가져와서 쓰겠다는 의미~
    @Autowired
    private BoardsRepository boardsRepository;
    // 보드 객체를 가져와서 쓰겠다는 의미~
    @Autowired
    private UserRepository userRepository;

    // 레파지토리는 쿼리만 작성 자세한 요구사항
    // ex) 유저가 로그인을 했다면 or 글을 작성한 유저라면

    // 댓글 작성 기능
    public Comments saveComment(Long boardId, Comments comment) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setBoards(board);

        return commentsRepository.save(comment);

    }


    // 댓글 수정 기능
    public Comments updateComment(Long commentId, Comments updatedcomment) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증된 사용자만 댓글을 수정할 수 있습니다.");
        }

        // 게시물과 작성자 정보 가져오기
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        String username = authentication.getName();

        // 게시물 작성자와 로그인한 사용자 비교
        if (!comment.getUser().getUserName().equals(username)) {
            throw new SecurityException("작성자만 게시물을 수정할 수 있습니다.");
        }

        // 게시물 내용 업데이트
        comment.setContent(updatedcomment.getContent());

        return commentsRepository.save(comment);

    }

    // 댓글 삭제 기능
    public void deleteComment(Long commentId) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증된 사용자만 댓글을 삭제할 수 있습니다.");
        }

        // 게시물과 작성자 정보 가져오기
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        String username = authentication.getName();

        // 게시물 작성자와 로그인한 사용자 비교
        if (!comment.getUser().getUserName().equals(username)) {
            throw new SecurityException("작성자만 댓글을 삭제할 수 있습니다.");
        }

        // 게시물 삭제 처리
        commentsRepository.deleteById(commentId);
    }

    // 좋아요 기능(보드의 조회수 기능과 다운이 되지 않는 다는 차이점만 존재)
//    public void viewCountUp(Long boardId) {
//        Board board = findById(boardId);
//        board.viewCountUp(board);
//    }
//    댓글 전체보기 기능(관리자 존재 시 필요할 수 있음)
//    public List<Comments> findAllComments(){
//        return commentsRepository.findAll();
//    }

}