package org.boot.post_springboot;

import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.domain.User;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.repository.UserRepository;
import org.boot.post_springboot.demo.service.BoardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BoardsServiceTest {

    private static final Logger log = LoggerFactory.getLogger(BoardsServiceTest.class);
    @InjectMocks
    private BoardsService boardsService;

    @Mock
    private BoardsRepository boardsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    private User user;
    private Boards board;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserName("DSDSD"); // 사용자 ID 설정
        board = new Boards();
        board.setTitle("Test Title");
        board.setContent("Test Content");


        log.info("게시판 제목: " + board.getContent());
        // 인증된 사용자 설정
        when(authentication.getName()).thenReturn("DSDSD");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void saveBoard_ShouldSaveBoard_WhenAuthenticatedUser() {
        // Arrange
        when(userRepository.findByUserName("DSDSD")).thenReturn(Optional.of(user));
        when(boardsRepository.save(any(Boards.class))).thenReturn(board);

        // Act
        Boards savedBoard = boardsService.saveBoard(board);

        // Assert
        assertNotNull(savedBoard);
        assertEquals(board.getTitle(), savedBoard.getTitle());
        assertEquals(user, savedBoard.getUser());
        verify(boardsRepository, times(1)).save(board);
        log.info("게시판 제목: " + board.getTitle());
    }

    @Test
    public void saveBoard_ShouldThrowSecurityException_WhenUserNotAuthenticated() {
        // Arrange
        SecurityContextHolder.clearContext(); // 인증을 초기화

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> boardsService.saveBoard(board));
        assertEquals("인증된 사용자만 게시물을 작성할 수 있습니다.", exception.getMessage());
    }

    @Test
    public void saveBoard_ShouldThrowIllegalArgumentException_WhenUserNotFound() {
        // Arrange
        when(userRepository.findByUserName("DSDSD")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> boardsService.saveBoard(board));
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }
}
