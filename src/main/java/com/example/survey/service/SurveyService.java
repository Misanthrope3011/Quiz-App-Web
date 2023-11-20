package com.example.survey.service;

import com.example.survey.entities.Question;
import com.example.survey.entities.QuizResult;
import com.example.survey.repository.QuestionRepository;
import com.example.survey.repository.QuizRepository;
import com.example.survey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final QuizRepository quizRepository;

	public Long countSurveyPoints(List<Question> submittedAnswers) {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userRepository.findByUsername(currentUserName).orElseThrow().getId();

		long resultPoints = submittedAnswers.stream()
				.map(this::mapToPairQuestionAndUserAnswer)
				.filter(pair -> pair.getFirst().getCorrectAnswer().equals(pair.getSecond()))
				.count();
		saveQuizResults(submittedAnswers, userId, resultPoints);

		return resultPoints;
	}

	private void saveQuizResults(List<Question> submittedAnswers, Long userId, long resultPoints) {
		QuizResult quizResult = new QuizResult();
		quizResult.setResultPoint(resultPoints);
		quizResult.setUserId(userId);
		quizResult.setQuizSize((long) submittedAnswers.size());
		quizRepository.save(quizResult);
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

	public Optional<Question> getQuestionById(Long id) {
		return questionRepository.findById(id);
	}

	public void deleteQuestion(Long id) {
		questionRepository.deleteById(id);
	}

}
