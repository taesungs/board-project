package com.example.board.controller;

import com.example.board.dto.AuthPrincipal;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.request.BoardUpdateRequest;
import com.example.board.dto.response.BoardResponse;
import com.example.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    } // @RequiredArgsConstructor 역할과 동일

    @PostMapping
    public ResponseEntity<Long> createBoard(
            @RequestBody BoardRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long id = boardService.createBoard(request, principal.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public BoardResponse getOne(@PathVariable Long id) {
        return boardService.getOne(id);
    }

    @GetMapping
    public List<BoardResponse> getAll() {
        return boardService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        boardService.updateBoard(id, request, principal.id());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        boardService.deleteBoard(id, principal.id());
        return ResponseEntity.noContent().build();
    }
}