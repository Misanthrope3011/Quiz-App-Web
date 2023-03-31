package com.example.survey.service;

import com.example.survey.entities.Question;
import com.example.survey.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

	@InjectMocks
	private SurveyService surveyService;

	@Mock
	private QuestionRepository questionRepository;

	@Test
	void shouldReturnCorrectNumberOfPoints() {
		List<Question> questions = getQuestionSet();
		List<Question> userAnswers = getUserAnswers();
		when(questionRepository.findById(anyLong())).thenAnswer(answer -> {
			Long value = (answer.getArgument(0));
			return Optional.of(questions.get(value.intValue()));
		});

		Long points = surveyService.countSurveyPoints(userAnswers);

		assertEquals(3, points, "Test should return correct number of points");
	}

	private List<Question> getQuestionSet() {
		List<Question> questionSet = new ArrayList<>();
		for (long i = 0; i < 5; i++) {
			Question question = new Question();
			question.setId(i);
			question.setCorrectAnswer("test" + i);
			questionSet.add(question);
		}

		return questionSet;
	}

	private List<Question> getUserAnswers() {
		List<Question> userAnswers = new ArrayList<>();
		for (long i = 0; i < 3; i++) {
			Question question = new Question();
			question.setId(i);
			question.setUserAnswer("test" + i);
			userAnswers.add(question);
		}

		return userAnswers;
	}

}