package com.example.survey.Services;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.Repositories.CategoryRepository;
import com.example.survey.Repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
@AllArgsConstructor
public class SurveyGenerationHelper {

    CategoryRepository categoryRepository;
    QuestionRepository questionRepository;

    public Set<Question> getQuestions(long surveySize, String categoryName) throws FieldNotFoundException {

        List<Question> modifiableRepositoryCopy = categoryRepository.findByName(categoryName.toUpperCase())
                .orElseThrow(() -> new FieldNotFoundException("Field with this name was not found"))
                .getCategorizedQuestions();

        Set<Question> surveyQuestions = new HashSet<>();

        for (int i = 0; i < surveySize; i++) {
            int currentGeneratedNumber = (int) (Math.random() * modifiableRepositoryCopy.size());
            surveyQuestions.add(modifiableRepositoryCopy.get(currentGeneratedNumber));
            modifiableRepositoryCopy.remove(currentGeneratedNumber);
        }

        return surveyQuestions;
    }

    public Question addQuestion(Question question) {

        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }


}
