package org.example.volunteerapp.repository;

import org.example.volunteerapp.entity.Request;
import org.example.volunteerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByElderly(User elderly);
}