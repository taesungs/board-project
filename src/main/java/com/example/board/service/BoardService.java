package com.example.board.service;

import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.request.BoardUpdateRequest;
import com.example.board.dto.response.BoardResponse;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createBoard(BoardRequest req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        Board board = new Board(req.title(), req.content(), user);
        return boardRepository.save(board).getId();
    }

    public BoardResponse getOne(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id=" + id));
        return new BoardResponse(board);
    }

    public List<BoardResponse> getAll() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::new)
                .toList();
    }

    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequest req, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        board.update(req.title(), req.content());
    }

    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);
    }
}