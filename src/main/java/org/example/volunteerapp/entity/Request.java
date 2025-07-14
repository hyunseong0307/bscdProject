package org.example.volunteerapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elderly_id", nullable = false)
    private User elderly; // 요청한 노인

    @Column(nullable = false)
    private String region; // 희망 지역

    @Lob // 긴 텍스트를 위한 어노테이션
    private String content; // 요청 내용

    @Column(nullable = false)
    private String status; // 요청 상태 (e.g., "PENDING", "MATCHED", "COMPLETED")

    @CreationTimestamp
    private LocalDateTime createdAt; // 요청 생성 시간
}