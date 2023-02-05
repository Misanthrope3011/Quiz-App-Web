package com.example.survey.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "SVY_QUESTIONS")
public class Question {

    @Id
    @Column(name = "qst_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "qst_content")
    private String question;

    @Size(min = 5, max = 200)
    @NotBlank(message = "Field is mandatory")
    @Column(name = "qst_answer_A")
    private String answerA;

    @Size(max = 200)
    @NotBlank(message = "Answer 1 is mandatory")
    @Column(name = "qst_answer_B")
    private String answerB;

    @Column(name = "qst_answer_C")
    @NotBlank(message = "Answer 2 is mandatory")
    @Size(min = 5, max = 200)
    private String answerC;

    @Column(name = "qst_answer_D")
    @NotBlank(message = "Answer 2 is mandatory")
    @Size(min = 5, max = 200)
    private String answerD;

    @JsonIgnore
    @Size(min = 5, max = 200)
    @NotBlank(message = "Correct answer is mandatory")
    @Column(name = "qst_answer_correct")
    private String correctAnswer;

    @Column(name = "qst_image")
    private byte[] image;

    @Column(name = "qst_answer_time")
    private Integer answerTimeSec;

    @JoinColumn(name = "qst_category_id")
    private Long category;

    @JsonIgnore
    @Transient
    private String categoryParser;


}

