//package demo.controller;
//
//import org.boot.post_springboot.demo.controller.AuthController;
//import org.boot.post_springboot.demo.domain.User;
//import org.boot.post_springboot.demo.dto.UserDTO;
//import org.boot.post_springboot.demo.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class AuthControllerTest {
//
//    @InjectMocks
//    private AuthController authController;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    private MockHttpSession session;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        session = new MockHttpSession();
//    }
//
//    @Test
//    void loginSuccess() {
//        // Given
//        UserDTO loginDto = new UserDTO();
//        loginDto.setUserEmail("user@example.com");
//        loginDto.setPassword("password123");
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
//
//        when(userService.findByUserEmail(anyString())).thenReturn(new User());
//
//        // When
//        ResponseEntity<?> response = authController.login(loginDto, session);
//
//        // Then
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(session.getAttribute("USER"));
//        verify(userService, times(1)).findByUserEmail(loginDto.getUserEmail());
//        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//    }
//
//    @Test
//    void loginFailure() {
//        // Given
//        UserDTO loginDto = new UserDTO();
//        loginDto.setUserEmail("user@example.com");
//        loginDto.setPassword("wrongPassword");
//
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenThrow(new AuthenticationException("Authentication failed") {});
//
//        // When
//        Exception exception = assertThrows(AuthenticationException.class, () -> {
//            authController.login(loginDto, session);
//        });
//
//        // Then
//        assertTrue(exception.getMessage().contains("Authentication failed"));
//        assertNull(session.getAttribute("USER"));
//        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(userService, never()).findByUserEmail(anyString());
//    }
//}
