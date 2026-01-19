package com.example.board.dto.response;

import com.example.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
    }
}