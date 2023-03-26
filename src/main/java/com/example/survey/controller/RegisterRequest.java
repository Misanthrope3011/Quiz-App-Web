package com.example.survey.controller;

import com.example.survey.validators.annotation.UniqueUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@UniqueUser
public class RegisterRequest {

	@Size(min = 5, max = 50)
	private String firstName;

	@Size(min = 5, max = 50)
	private String lastName;

	@NotBlank
	@Size(min = 5, max = 50)
	private String password;

	@NotBlank
	@Size(min = 5, max = 50)
	private String username;

}




