package com.example.survey.service;

import com.example.survey.entities.Question;
import com.example.survey.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final QuestionRepository questionRepository;

	public Long countSurveyPoints(List<Question> submittedAnswers) {

		return submittedAnswers.stream()
				.map(this::mapToPairQuestionAndUserAnswer)
				.filter(pair -> pair.getFirst().getCorrectAnswer().equals(pair.getSecond()))
				.count();
	}

	private Pair<Question, String> mapToPairQuestionAndUserAnswer(Question answer) {
		return Pair.of(questionRepository.findById(answer.getId()).orElseThrow(), answer.getUserAnswer());
	}

	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

}
