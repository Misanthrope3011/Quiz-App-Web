package com.example.survey.controller;

import com.example.survey.dto.AuthenticationRequest;
import com.example.survey.dto.UserDTO;
import com.example.survey.service.AuthService;
import com.example.survey.service.SurveyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
@AllArgsConstructor
public class SurveyGeneratorController {

	private final AuthService authService;
	private final SurveyService surveyService;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody @Validated RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<UserDTO> authenticationPoint(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

}
