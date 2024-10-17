//package org.boot.post_springboot;
//import org.boot.post_springboot.demo.domain.Boards;
//import org.boot.post_springboot.demo.domain.Comments;
//import org.boot.post_springboot.demo.domain.User;
//import org.boot.post_springboot.demo.repository.BoardsRepository;
//import org.boot.post_springboot.demo.repository.CommentsRepository;
//import org.boot.post_springboot.demo.repository.UserRepository;
//import org.boot.post_springboot.demo.service.CommentsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//        import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CommentsServiceTest {
//
//    @InjectMocks
//    private CommentsService commentsService;
//
//    @Mock
//    private CommentsRepository commentsRepository;
//
//    @Mock
//    private BoardsRepository boardsRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    private Boards board;
//    private User user;
//    private Comments comment;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // 테스트 데이터 설정
//        board = new Boards();
//        board.setBoardId(1L); // 적절한 ID 설정
//
//        user = new User();
//        user.setUserName("testUser"); // 적절한 사용자 이름 설정
//
//        comment = new Comments();
//        comment.setContent("테스트 댓글");
//        comment.setUser(user);
//        comment.setBoards(board);
//    }
//
//    @Test
//    void saveComment_shouldSaveComment_whenBoardExists() {
//        when(boardsRepository.findById(UUID.)).thenReturn(Optional.of(board));
//        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);
//
//        Comments savedComment = commentsService.saveComment(1L, comment);
//
//        assertNotNull(savedComment);
//        assertEquals("테스트 댓글", savedComment.getContent());
//        verify(commentsRepository, times(1)).save(comment);
//    }
//
//    @Test
//    void updateComment_shouldUpdateComment_whenUserIsAuthor() {
//        comment.setUser(user); // 댓글 작성자를 설정
//
//        when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment));
//        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);
//
//        Comments updatedComment = new Comments();
//        updatedComment.setContent("업데이트된 댓글");
//
//        Comments result = commentsService.updateComment(1L, updatedComment);
//
//        assertEquals("업데이트된 댓글", result.getContent());
//        verify(commentsRepository, times(1)).save(comment);
//    }
//
//    @Test
//    void deleteComment_shouldDeleteComment_whenUserIsAuthor() {
//        when(commentsRepository.findById(1L)).thenReturn(Optional.of(comment));
//
//        assertDoesNotThrow(() -> commentsService.deleteComment(1L));
//        verify(commentsRepository, times(1)).deleteById(1L);
//    }
//}
