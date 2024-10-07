package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.repository.BoardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.boot.post_springboot.demo.domain.Boards;

import java.util.List;

@Service
public interface BoardService {
    List<Boards> boardList();
}
