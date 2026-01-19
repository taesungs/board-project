package com.example.board.dto;

import com.example.board.entity.User;

public record AuthPrincipal(Long id, String username) {

    public static AuthPrincipal from(User user) {
        return new AuthPrincipal(user.getId(), user.getUsername());
    }
}