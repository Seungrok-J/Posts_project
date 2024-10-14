package org.boot.post_springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.boot.post_springboot.demo.controller.CommerntContoller;
import org.boot.post_springboot.demo.domain.Comments;
import org.boot.post_springboot.demo.repository.CommentsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommerntContollerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentsRepository commentsRepository;

    @InjectMocks
    private CommerntContoller commerntContoller;

    private Comments comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트 댓글 데이터 설정
        comment = new Comments();
        comment.setContent("테스트 댓글");
    }

    @Test
    void saveComment_shouldReturnCreatedStatus() throws Exception {
        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/board/comment/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteComment_shouldReturnNoContentStatus() throws Exception {
        doNothing().when(commentsRepository).delete(any(Comments.class));

        mockMvc.perform(delete("/api/board/comment/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateComment_shouldReturnOkStatus() throws Exception {
        when(commentsRepository.save(any(Comments.class))).thenReturn(comment);

        mockMvc.perform(put("/api/board/comment/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isOk());
    }
}
