package com.example.survey.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    String question;

    @ManyToOne
    @JoinColumn
    @NotBlank(message = "Field is mandatory")
    private Category category;

    @Size(min = 5, max = 200)
    @NotBlank(message = "Field is mandatory")
    String answerA;
    @Size(max = 200)
    @NotBlank(message = "Answer 1 is mandatory")
    String answerB;
    @NotBlank(message = "Answer 2 is mandatory")
    @Size(min = 5, max = 200)
    String answerC;
    @NotBlank(message = "Answer 2 is mandatory")
    @Size(min = 5, max = 200)
    String answerD;
    @JsonIgnore
    @Size(min = 5, max = 200)
    @NotBlank(message = "Correct answer is mandatory")
    String correctAnswer;

    byte[] image;

    Integer answerTimeSec;

    public void setImage(String image) {
        this.image = image.getBytes();
    }

    @JsonIgnore
    @Transient
    String categoryParser;
}

