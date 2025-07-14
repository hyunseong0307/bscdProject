package org.example.volunteerapp.repository;

import org.example.volunteerapp.entity.Match;
import org.example.volunteerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    // 봉사자로 매칭 내역 찾기
    List<Match> findByVolunteer(User volunteer);

    // 노인으로 매칭 내역 찾기 (Request를 통해)
    @Query("SELECT m FROM Match m WHERE m.request.elderly = :elderly")
    List<Match> findByElderly(@Param("elderly") User elderly);
}