package com.example.board.service;

import com.example.board.dto.request.LoginRequest;
import com.example.board.dto.request.SignUpRequest;
import com.example.board.dto.response.LoginResponse;
import com.example.board.entity.User;
import com.example.board.global.security.JwtProvider;
import com.example.board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignUpRequest request) {

        //username 중복 체크
        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 존재하는 username입니다."
            );
        }

        //비밀번호 해시
        String encodedPassword =
                passwordEncoder.encode(request.password());

        //사용자 저장
        User user = new User(
                request.username(),
                encodedPassword
        );

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유저가 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "비밀번호가 일치하지 않습니다."
            );
        }
        String token = jwtProvider.createAccessToken(user.getId(),user.getUsername());
        return new LoginResponse(token);

    }


}

