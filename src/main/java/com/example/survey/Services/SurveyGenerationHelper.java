package com.example.survey.Services;

import com.example.survey.Entities.Question;
import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.Repositories.CategoryRepository;
import com.example.survey.Repositories.QuestionRepository;
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
public class SurveyGenerationHelper {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestions(long surveySize, String categoryName) throws FieldNotFoundException {
        List<Question> surveyQuestions = new ArrayList<>();
        List<Question> modifiableRepositoryCopy = getCategorizedQuestions(categoryName);
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
        Long questionId = categoryRepository.findByName(categoryName).orElseThrow().getId();
        return questionRepository.findAllByCategory(questionId);
    }

}
