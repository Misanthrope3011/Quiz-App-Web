package com.example.survey.service;

import com.example.survey.controller.AuthenticationRequest;
import com.example.survey.controller.AuthenticationResponse;
import com.example.survey.controller.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;


	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()));

		return null;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		return null;
	}

}
