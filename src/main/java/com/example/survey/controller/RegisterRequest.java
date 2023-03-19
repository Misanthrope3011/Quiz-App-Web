package com.example.survey.controller;

import com.example.survey.validators.annotation.UniqueUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UniqueUser
public class RegisterRequest {

	private String firstName;
	private String lastName;

	@NotBlank
	private String password;

	@NotBlank
	private String username;

}
