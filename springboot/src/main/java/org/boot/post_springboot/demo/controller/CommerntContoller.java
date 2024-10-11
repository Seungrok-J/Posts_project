package org.boot.post_springboot.demo.controller;

import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.Comments;
import org.boot.post_springboot.demo.repository.CommentsRepository;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/comment")
@RequiredArgsConstructor
public class CommerntContoller {

    @Autowired
    CommentsRepository commentsRepository;

    @PostMapping("/save")
    private Comments save(@RequestBody Comments comments) {
        return commentsRepository.save(comments);
    }

    @GetMapping("/delete")
    private void delete(@RequestBody Comments comments) {
        commentsRepository.delete(comments);
    }

    @PutMapping("/update")
    private Comments update(@RequestBody Comments comments) {
        return commentsRepository.save(comments);
    }





}
