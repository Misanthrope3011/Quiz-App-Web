package com.example.survey.controller;

import com.example.survey.entities.Question;
import com.example.survey.pojo.CategoryPOJO;
import com.example.survey.service.SurveyQuestionGeneratorService;
import com.example.survey.service.SurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final SurveyQuestionGeneratorService generationHelper;
	private final ObjectMapper objectMapper;
	private final SurveyService surveyService;

	@PostMapping("/generateSurvey")
	public ResponseEntity<List<Question>> generate(@RequestBody CategoryPOJO survey) {
		List<Question> surveyQuestions = generationHelper.getQuestions(survey.getSize(), survey.getCategory());

		return ResponseEntity.ok(surveyQuestions);
	}

	@PostMapping("/survey/submit")
	public ResponseEntity<Object> submitAnswers(@RequestBody List<Question> userAnswers) throws JsonProcessingException {
		return ResponseEntity.ok(objectMapper.writeValueAsString(String.format("You have got %d points out of %d", surveyService.countSurveyPoints(userAnswers), userAnswers.size())));
	}

}
