package com.example.survey.Services;

import com.example.survey.Entities.Question;
import com.example.survey.POJOs.AnswerDTO;
import com.example.survey.Repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final QuestionRepository questionRepository;

	public Long countSurveyPoints(List<AnswerDTO> submittedAnswers) {

		return submittedAnswers.stream()
				.map(this::mapToPairQuestionAndUserAnswer)
				.filter(pair -> pair.getFirst().getCorrectAnswer().equals(pair.getSecond()))
				.count();
	}

	private Pair<Question, String> mapToPairQuestionAndUserAnswer(AnswerDTO answer) {
		return Pair.of(questionRepository.findById(answer.getId()).orElseThrow(), answer.getUserAnswer());
	}

}
