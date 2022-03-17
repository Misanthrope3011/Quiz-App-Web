package com.example.survey.Controller;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.POJOs.POJO;
import com.example.survey.Services.CategoryService;
import com.example.survey.Services.SurveyGenerationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Getter
@Setter
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class SurveyGeneratorController {

    SurveyGenerationHelper helper;
    CategoryService categoryService;
    List<Question> surveyQuestions;

    @PostMapping("/generateSurvey")
    public ResponseEntity<List<Question>> generate(@RequestBody POJO survey) throws FieldNotFoundException {

        this.surveyQuestions = helper.getQuestions(survey.getSize(), survey.getCategory());

        return ResponseEntity.ok(this.surveyQuestions);
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Question> getById(@PathVariable Long id) throws FieldNotFoundException {

        return ResponseEntity.ok(this.surveyQuestions.get(Math.toIntExact(id)));
    }

    @PostMapping("/submit")
    public ResponseEntity submitAnswers() {

        return ResponseEntity.ok("Submited successfully");
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {

        question.setCategory(categoryService.getCategory(question.getCategoryParser()));

        return ResponseEntity.ok(helper.addQuestion(question));
    }

    @GetMapping("/getAllQuestions")
    public ResponseEntity<List<Question>> getAll() {
        return ResponseEntity.ok(helper.getAllQuestions());
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
