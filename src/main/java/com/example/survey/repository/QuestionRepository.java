package com.example.survey.repository;

import com.example.survey.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByCategory(Long id);

}
