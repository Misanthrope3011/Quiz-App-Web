package com.example.survey.Entities;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String question;

    @ManyToOne
    @JoinColumn
    private Category category;

    String answerA;
    String answerB;
    String answerC;
    String answerD;
    String correctAnswer;

    byte[] image;
    Integer answerTimeSec;

}
