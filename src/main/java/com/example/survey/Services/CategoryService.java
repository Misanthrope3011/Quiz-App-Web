package com.example.survey.Services;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category getCategory(String name) {
        return categoryRepository.findByName(name).orElseThrow(RuntimeException::new);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String name) {
        return  categoryRepository.findByName(name).orElseThrow(RuntimeException::new)
                    .getCategorizedQuestions();
    }

}
