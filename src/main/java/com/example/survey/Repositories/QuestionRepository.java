package com.example.survey.Repositories;

import com.example.survey.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByCategory(Long id);

}
