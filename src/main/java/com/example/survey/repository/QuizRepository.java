package com.example.survey.repository;

import com.example.survey.entities.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizResult, Long> {
}
