package com.example.survey.controller;

import com.example.survey.entities.Category;
import com.example.survey.entities.Question;
import com.example.survey.service.CategoryService;
import com.example.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final CategoryService categoryService;
	private final SurveyService surveyService;

	@PostMapping("/category/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {

		return ResponseEntity.ok(categoryService.addCategory(category));
	}

	@PostMapping("/question/add")
	public ResponseEntity<Question> addQuestionToDatabase(@RequestBody Question question) {
		question.setCategory(categoryService.getCategory(question.getCategoryParser()).getId());

		return ResponseEntity.ok(surveyService.addQuestion(question));
	}

	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllAvailableQuestions() {
		return ResponseEntity.ok(surveyService.getAllQuestions());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

}
