package com.example.survey;

import com.example.survey.Entities.Category;
import com.example.survey.Entities.Question;
import com.example.survey.Exceptions.FieldNotFoundException;
import com.example.survey.POJOs.POJO;
import com.example.survey.Services.CategoryService;
import com.example.survey.Services.SurveyGenerationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Getter
@Setter
public class SurveyControllerTest {

    @Mock
    SurveyGenerationHelper helper;

    @Mock
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void is_element_added() {
        int size = helper.getAllQuestions().size();
        Question question = new Question();
        helper.addQuestion(question);

        assertThat(size + 1).isEqualTo(helper.getAllQuestions().size());
    }

    @Test
    public void getConfigTest() throws Exception {

        POJO pojo = new POJO("INNE", 10);
        Set<Question> set = helper.getQuestions(pojo.getSize(), pojo.getCategory());
        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/generateSurvey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pojo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void isCategoryRepositoryNotEmpty(){

        List<Question> searchedCategory = categoryService.getQuestionsByCategory("INNE");

        assertThat(searchedCategory.size() > 0);
    }

    @Test
    public void isSurveyCreatedProperly() throws FieldNotFoundException {
        final long size = 4;
        if(helper.getAllQuestions().size() >= size) {
            assertThat(helper.getQuestions(4, "INNE").size() == size);
        } else {
            assertThat(helper.getQuestions(4, "INNE").size() == helper.getAllQuestions().size());
        }
    }

}
