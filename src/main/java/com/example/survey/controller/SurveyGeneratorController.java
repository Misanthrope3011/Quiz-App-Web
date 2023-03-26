package com.example.survey.controller;

import com.example.survey.dto.UserDTO;
import com.example.survey.entities.Category;
import com.example.survey.entities.Question;
import com.example.survey.pojo.CategoryPOJO;
import com.example.survey.service.AuthService;
import com.example.survey.service.CategoryService;
import com.example.survey.service.SurveyQuestionGeneratorService;
import com.example.survey.service.SurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Getter
@Setter
@AllArgsConstructor
public class SurveyGeneratorController {

	private final SurveyQuestionGeneratorService generationHelper;
	private final AuthService authService;
	private final CategoryService categoryService;
	private final SurveyService surveyService;
	private final ObjectMapper objectMapper;

	@PostMapping("/generateSurvey")
	public ResponseEntity<List<Question>> generate(@RequestBody CategoryPOJO survey) {
		List<Question> surveyQuestions = generationHelper.getQuestions(survey.getSize(), survey.getCategory());

		return ResponseEntity.ok(surveyQuestions);
	}

	@PostMapping("/survey/submit")
	public ResponseEntity<Object> submitAnswers(@RequestBody List<Question> userAnswers) throws JsonProcessingException {
		return ResponseEntity.ok(objectMapper.writeValueAsString(String.format("You have got %d points out of %d", surveyService.countSurveyPoints(userAnswers), userAnswers.size())));
	}

	@PostMapping("/question/add")
	public ResponseEntity<Question> addQuestionToDatabase(@RequestBody Question question) {
		question.setCategory(categoryService.getCategory(question.getCategoryParser()).getId());

		return ResponseEntity.ok(generationHelper.addQuestion(question));
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody @Validated RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<UserDTO> authenticationPoint(@RequestBody AuthenticationRequest request) {

		return ResponseEntity.ok(authService.authenticate(request));
	}

	@PostMapping("/category/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {

		return ResponseEntity.ok(categoryService.addCategory(category));
	}

	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllAvailableQuestions() {
		return ResponseEntity.ok(generationHelper.getAllQuestions());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

}
