package com.example.survey.controller;

import com.example.survey.entities.Category;
import com.example.survey.entities.Question;
import com.example.survey.service.CategoryService;
import com.example.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping("/questions/category/{code}")
	public ResponseEntity<List<Question>> getCategoryQuestions(@PathVariable String code) {

		return ResponseEntity.ok(categoryService.getQuestionsByCategory(code));
	}

	@PostMapping("/question/add")
	public ResponseEntity<Question> addQuestionToDatabase(@RequestBody Question question) {
		categoryService.getCategory(question.getCategory().getCode());

		return ResponseEntity.ok(surveyService.addQuestion(question));
	}

	@PutMapping("/question/{id}/edit")
	public ResponseEntity<Question> editQuestion(@PathVariable Long id, @RequestBody Question question) {
		if(surveyService.getQuestionById(id).isPresent()) {
			return ResponseEntity.ok(surveyService.addQuestion(question));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/questions")
	public ResponseEntity<List<Question>> getAllAvailableQuestions() {
		return ResponseEntity.ok(surveyService.getAllQuestions());
	}

	@DeleteMapping("/question/{id}/delete")
	public ResponseEntity<List<Question>> deleteQuestion(@PathVariable Long id) {
		surveyService.deleteQuestion(id);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/question/{id}")
	public ResponseEntity <Question>getQuestionById(@PathVariable Long id) {
		if(surveyService.getQuestionById(id).isPresent()) {
			return ResponseEntity.ok(surveyService.getQuestionById(id).get());
		}
		return ResponseEntity.notFound().build();
	}

}
