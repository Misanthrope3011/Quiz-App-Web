package com.example.survey;

import com.example.survey.Controller.SurveyController;
import com.example.survey.Entities.Question;
import com.example.survey.POJOs.POJO;
import com.example.survey.Services.SurveyGenerationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@WebMvcTest(SurveyController.class)
public class SurveyControllerTest {

    @MockBean
    SurveyGenerationHelper helper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getConfigTest() throws Exception {

        POJO pojo = new POJO("INNE", 10);
        Set<Question> set = helper.getQuestions(pojo.getSize(), pojo.getCategory());

        mockMvc.perform(MockMvcRequestBuilders.post("/generateSurvey")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pojo))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();
    }

}
