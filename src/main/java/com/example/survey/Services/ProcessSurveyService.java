package com.example.survey.Services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
public class ProcessSurveyService {

    private int questionIndex;

    public ProcessSurveyService() {
        this.questionIndex = 0;
    }


}
