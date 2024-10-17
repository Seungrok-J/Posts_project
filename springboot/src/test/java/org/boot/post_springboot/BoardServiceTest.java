//package org.boot.post_springboot;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.boot.post_springboot.demo.domain.Boards;
//import org.boot.post_springboot.demo.domain.User;
//import org.boot.post_springboot.demo.repository.BoardsRepository;
//import org.boot.post_springboot.demo.repository.UserRepository;
//import org.boot.post_springboot.demo.service.BoardsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//public class BoardServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private BoardsRepository boardsRepository;
//
//    @InjectMocks
//    private BoardsService boardService;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private SecurityContext securityContext;
//
//    private User user;
//    private Boards board;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        user = new User();
//        user.setUserId(Long.valueOf(1));
//        user.setUserName("testUser");
//
//        board = new Boards();
//        board.setBoardId(Long.valueOf(1));
//        board.setTitle("Test Title");
//        board.setContent("Test Content");
//        board.setUser(user);
//
//        // Mock 인증된 사용자 설정
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//    }
//
//    @Test
//    public void testSaveBoard_Success() { // 인증 유저 글 작성 테스트 -> 성공
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(authentication.getName()).thenReturn("testUser");
//        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
//        when(boardsRepository.save(any(Boards.class))).thenReturn(board);
//
//        Boards savedBoard = boardService.saveBoard(board);
//
//        assertNotNull(savedBoard);
//        assertEquals("Test Title", savedBoard.getTitle());
//    }
//
//    @Test
//    public void testUpdateBoard_Success() {// 인증 유저 글 수정 테스트 -> 성공
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(authentication.getName()).thenReturn("testUser");
//        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
//        when(boardsRepository.findById(anyLong())).thenReturn(Optional.of(board));
//        when(boardsRepository.save(any(Boards.class))).thenReturn(board);
//
//        Boards updatedBoard = new Boards();
//        updatedBoard.setTitle("Updated Title");
//        updatedBoard.setContent("Updated Content");
//
//        Boards result = boardService.updateBoard(1L, updatedBoard);
//
//        assertNotNull(result);
//        assertEquals("Updated Title", result.getTitle());
//        assertEquals("Updated Content", result.getContent());
//    }
//
//    @Test
//    public void testUpdateBoard_NotOwner() { // 인증 유저이나 작성자가 아닌 유저 글 수정 테스트 -> 실패
//        User otherUser = new User();
//        otherUser.setUserId(Long.valueOf(2));
//
//        board.setUser(otherUser);
//
//        when(authentication.isAuthenticated()).thenReturn(true);
//        when(authentication.getName()).thenReturn("testUser");
//        when(userRepository.findByUserName("testUser")).thenReturn(Optional.of(user));
//        when(boardsRepository.findById(anyLong())).thenReturn(Optional.of(board));
//
//        SecurityException exception = assertThrows(SecurityException.class, () -> {
//            boardService.updateBoard(1L, board);
//        });
//
//        assertEquals("작성자만 게시물을 수정할 수 있습니다.", exception.getMessage());
//    }
//}
