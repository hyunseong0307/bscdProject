package org.example.volunteerapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.volunteerapp.entity.Match;
import org.example.volunteerapp.entity.Request;
import org.example.volunteerapp.entity.User;
import org.example.volunteerapp.repository.UserRepository;
import org.example.volunteerapp.service.MyPageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final UserRepository userRepository;
    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // UserDetails에서 이메일을 가져와 DB에서 전체 User 정보를 조회
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        model.addAttribute("user", currentUser);

        // 역할에 따라 다른 데이터를 모델에 추가
        if ("ELDERLY".equals(currentUser.getRole())) {
            List<Request> requests = myPageService.getMyRequests(currentUser);
            List<Match> matches = myPageService.getMyMatches(currentUser);
            model.addAttribute("requests", requests);
            model.addAttribute("matches", matches);
        } else if ("VOLUNTEER".equals(currentUser.getRole())) {
            List<Match> matches = myPageService.getMyMatches(currentUser);
            model.addAttribute("matches", matches);
        }

        return "mypage";
    }
}