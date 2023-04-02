package com.example.survey.controller;

import com.example.survey.entities.Category;
import com.example.survey.pojo.PasswordChangeRequest;
import com.example.survey.entities.Question;
import com.example.survey.pojo.CategoryPOJO;
import com.example.survey.service.AuthService;
import com.example.survey.service.CategoryService;
import com.example.survey.service.SurveyQuestionGeneratorService;
import com.example.survey.service.SurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private final CategoryService categoryService;
	private final AuthService authService;

	@PostMapping("/generateSurvey")
	public ResponseEntity<Object> generate(@RequestBody CategoryPOJO survey) throws JsonProcessingException {
		List<Question> surveyQuestions = generationHelper.getQuestions(survey.getSize(), survey.getCategory());

		if (surveyQuestions.size() > 0) {
			return ResponseEntity.ok(surveyQuestions);
		}


		return ResponseEntity.status(HttpStatus.NOT_FOUND).body((objectMapper.writeValueAsString("There are no questions for this category yet")));
	}

	@PostMapping("/survey/submit")
	public ResponseEntity<Object> submitAnswers(@RequestBody List<Question> userAnswers) throws JsonProcessingException {
		return ResponseEntity.ok(objectMapper.writeValueAsString(String.format("You have got %d points out of %d", surveyService.countSurveyPoints(userAnswers), userAnswers.size())));
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@PostMapping("/passwordChange")
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
		return authService.passwordChange(passwordChangeRequest);
	}

}
