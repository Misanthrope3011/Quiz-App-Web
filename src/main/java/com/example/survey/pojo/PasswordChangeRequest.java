package com.example.survey.pojo;

import lombok.Getter;

@Getter
public class PasswordChangeRequest {

    String newPassword;
    String oldPassword;

}
