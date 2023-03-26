package com.example.survey.service;

import com.example.survey.entities.Category;
import com.example.survey.entities.Question;
import com.example.survey.repository.CategoryRepository;
import com.example.survey.repository.QuestionRepository;
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
        return categoryRepository.findByCode(name).orElseThrow();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String code) {
        Long categoryId = categoryRepository.findByCode(code).orElseThrow().getId();

        return questionRepository.findAllByCategory(categoryId);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

}
