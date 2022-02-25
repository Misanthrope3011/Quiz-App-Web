package com.example.survey.Controller;

import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.POJOs.POJO;
import com.example.survey.Services.SurveyGenerationHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Getter
@Setter
@AllArgsConstructor
public class SurveyController {

    SurveyGenerationHelper helper;

    @PostMapping("/generateSurvey")
    public ResponseEntity generate(@RequestBody POJO surveyConfig) throws FieldNotFoundException {

        return ResponseEntity.ok(helper.getQuestions(surveyConfig.getSize(), surveyConfig.getCategory()));
    }

}
