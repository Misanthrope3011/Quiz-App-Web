package com.example.survey.service;

import com.example.survey.entities.Question;
import com.example.survey.repository.CategoryRepository;
import com.example.survey.repository.QuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class SurveyQuestionGeneratorService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public List<Question> getQuestions(long surveySize, String categoryCode) {
        List<Question> surveyQuestions = new ArrayList<>();
        List<Question> modifiableRepositoryCopy = getCategorizedQuestions(categoryCode);
        generateQuizQuestions(surveySize, surveyQuestions, modifiableRepositoryCopy);

        return surveyQuestions;
    }

    private void generateQuizQuestions(long surveySize, List<Question> surveyQuestions, List<Question> modifiableRepositoryCopy) {
        for (int i = 0; i < surveySize && modifiableRepositoryCopy.size() > 0; i++) {
            int currentGeneratedNumber = (int) (Math.random() * modifiableRepositoryCopy.size());
            surveyQuestions.add(modifiableRepositoryCopy.get(currentGeneratedNumber));
            modifiableRepositoryCopy.remove(currentGeneratedNumber);
        }
    }

    private List<Question> getCategorizedQuestions(String categoryName) {
        Long questionId = categoryRepository.findByCode(categoryName).orElseThrow().getId();

        return questionRepository.findAllByCategory(questionId);
    }

}
