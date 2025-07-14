package org.example.volunteerapp.service;

import lombok.RequiredArgsConstructor;
import org.example.volunteerapp.entity.Match;
import org.example.volunteerapp.entity.Request;
import org.example.volunteerapp.entity.User;
import org.example.volunteerapp.repository.MatchRepository;
import org.example.volunteerapp.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final RequestRepository requestRepository;
    private final MatchRepository matchRepository;

    public List<Request> getMyRequests(User user) {
        if ("ELDERLY".equals(user.getRole())) {
            return requestRepository.findByElderly(user);
        }
        return Collections.emptyList(); // 빈 리스트 반환
    }

    public List<Match> getMyMatches(User user) {
        if ("ELDERLY".equals(user.getRole())) {
            return matchRepository.findByElderly(user);
        } else if ("VOLUNTEER".equals(user.getRole())) {
            return matchRepository.findByVolunteer(user);
        }
        return Collections.emptyList();
    }
}