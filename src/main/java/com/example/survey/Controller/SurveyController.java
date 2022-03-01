package com.example.survey.Controller;

import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.POJOs.POJO;
import com.example.survey.Services.SurveyGenerationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Getter
@Setter
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class SurveyController {

    SurveyGenerationHelper helper;

    @PostMapping("/generateSurvey")
    public ResponseEntity generate(@RequestBody POJO survey ) throws FieldNotFoundException {

        return ResponseEntity.ok(helper.getQuestions(survey.getSize(), survey.getCategory()));
    }

    @PostMapping("/submit")
    public ResponseEntity submitAnswers() {

        return ResponseEntity.ok("Submited successfully");
    }
}
