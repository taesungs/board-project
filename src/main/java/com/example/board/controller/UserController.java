package com.example.board.controller;

import com.example.board.dto.request.LoginRequest;
import com.example.board.dto.request.SignUpRequest;
import com.example.board.dto.response.LoginResponse;
import com.example.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
            @RequestBody SignUpRequest request
    ) {
        authService.signup(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ){
        authService.login(request);
        return ResponseEntity.ok(authService.login(request));
    }
}

