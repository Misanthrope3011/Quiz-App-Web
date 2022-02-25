package com.example.survey.Exceptions;

import lombok.AllArgsConstructor;

public class FieldNotFoundException extends Exception {

    public FieldNotFoundException(String message) {
        super(message);
    }
}
