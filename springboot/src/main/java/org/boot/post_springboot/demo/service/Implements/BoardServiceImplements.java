package org.boot.post_springboot.demo.service.Implements;

import lombok.RequiredArgsConstructor;
import org.boot.post_springboot.demo.domain.Boards;
import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.boot.post_springboot.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImplements implements BoardService {
    @Autowired
    private final BoardsRepository boardsRepository;

    @Override
    public List<Boards> boardList() {

        return boardsRepository.findAll();
    }


}
