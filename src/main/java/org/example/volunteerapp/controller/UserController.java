package org.example.volunteerapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.volunteerapp.dto.*;
import org.example.volunteerapp.jwt.JwtUtil;
import org.example.volunteerapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.register(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
        var user = userService.authenticate(requestDto);
        String token = jwtUtil.createToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponseDto(token, user.getRole()));
    }
}
