package com.example.survey.Entities;

import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    String question;

    @ManyToOne
    @JoinColumn
    @NotBlank(message = "Field is mandatory")
    private Category category;

    @Size(max = 200)
    @NotBlank(message = "Field is mandatory")
    String answerA;
    @Size(max = 200)
    @NotBlank(message = "Answer 1 is mandatory")
    String answerB;
    @Size(max = 200)
    String answerC;
    @Size(max = 200)
    @NotBlank(message = "Answer 2 is mandatory")
    String answerD;
    @Size(max = 200)
    @NotBlank(message = "Correct answer is mandatory")
    String correctAnswer;

    byte[] image;

    Integer answerTimeSec;

}
