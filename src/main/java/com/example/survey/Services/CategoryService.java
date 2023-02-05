package com.example.survey.Services;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Repositories.CategoryRepository;
import com.example.survey.Repositories.QuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public Category getCategory(String name) {
        return categoryRepository.findByName(name).orElseThrow();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String name) {
        Long categoryId = categoryRepository.findByName(name).orElseThrow().getId();

        return questionRepository.findAllByCategory(categoryId);
    }

}
