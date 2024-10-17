//package org.boot.post_springboot;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.boot.post_springboot.demo.controller.BoardController;
//import org.boot.post_springboot.demo.domain.Boards;
//import org.boot.post_springboot.demo.service.BoardsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//
//
//public class BoardControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private BoardsService boardService;
//
//    @InjectMocks
//    private BoardController boardController;
//
//    private Boards board;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
//
//        board = new Boards();
//        board.setBoardId(Long.valueOf(1));
//        board.setTitle("Test Title");
//        board.setContent("Test Content");
//    }
//
//    @Test
//    public void testSaveBoard_Success() throws Exception { // 게시물 작성 API 테스트 -> 성공
//        when(boardService.saveBoard(any(Boards.class))).thenReturn(board);
//
//        mockMvc.perform(post("/boards/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(board)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Test Title"))
//                .andExpect(jsonPath("$.content").value("Test Content"));
//
//        verify(boardService, times(1)).saveBoard(any(Boards.class));
//    }
//
//    @Test
//    public void testUpdateBoard_Success() throws Exception { // 게시물 수정 API 테스트 -> 성공
//        Boards updatedBoard = new Boards();
//        updatedBoard.setTitle("Updated Title");
//        updatedBoard.setContent("Updated Content");
//
//        when(boardService.updateBoard(anyLong(), any(Boards.class))).thenReturn(updatedBoard);
//
//        mockMvc.perform(put("/boards/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updatedBoard)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Updated Title"))
//                .andExpect(jsonPath("$.content").value("Updated Content"));
//
//        verify(boardService, times(1)).updateBoard(anyLong(), any(Boards.class));
//    }
//
//    @Test
//    public void testUpdateBoard_NotAuthorized() throws Exception { // 타유저 게시물 수정 API 테스트 -> 실패
//        doThrow(new SecurityException("작성자만 게시물을 수정할 수 있습니다."))
//                .when(boardService).updateBoard(anyLong(), any(Boards.class));
//
//        mockMvc.perform(put("/boards/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(board)))
//                .andExpect(status().isUnauthorized())
//                .andExpect(result -> assertEquals("작성자만 게시물을 수정할 수 있습니다.", result.getResolvedException().getMessage()));
//
//        verify(boardService, times(1)).updateBoard(anyLong(), any(Boards.class));
//    }
//}
