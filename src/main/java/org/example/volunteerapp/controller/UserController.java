package org.example.volunteerapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.volunteerapp.dto.SignupRequestDto;
import org.example.volunteerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // private final JwtUtil jwtUtil; // JWT 불필요, 제거

    // 회원가입 페이지를 보여주는 GET 메서드
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signup";
    }

    // 회원가입 요청을 처리하는 POST 메서드
    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute SignupRequestDto requestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup"; // 유효성 검사 실패 시 다시 회원가입 폼으로
        }

        try {
            userService.register(requestDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup"; // 서비스 로직에서 에러 발생 시 메시지와 함께 폼으로
        }

        return "redirect:/login"; // 성공 시 로그인 페이지로 리다이렉트
    }

    // 로그인 처리는 SecurityConfig의 formLogin이 담당하므로, 로그인 관련 메서드는 제거합니다.
}