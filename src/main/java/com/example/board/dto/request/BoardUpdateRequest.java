package com.example.board.dto.request;


public record BoardUpdateRequest (
  String title,
  String content
) {}
