package com.example.survey.Controller;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.POJOs.CategoryPOJO;
import com.example.survey.Services.CategoryService;
import com.example.survey.Services.SurveyGenerationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Getter
@Setter
@AllArgsConstructor
public class SurveyGeneratorController {

    private final SurveyGenerationHelper helper;
    private final CategoryService categoryService;
    private List<Question> surveyQuestions;

    @PostMapping("/generateSurvey")
    public ResponseEntity<List<Question>> generate(@RequestBody CategoryPOJO survey) throws FieldNotFoundException {
        surveyQuestions = helper.getQuestions(survey.getSize(), survey.getCategory());

        return ResponseEntity.ok(surveyQuestions);
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Question> getById(@PathVariable Long id)  {
        return ResponseEntity.ok(this.surveyQuestions.get(Math.toIntExact(id)));
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitAnswers() {
        return ResponseEntity.ok("Submitted successfully");
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        question.setCategory(categoryService.getCategory(question.getCategoryParser()).getId());

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
