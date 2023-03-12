package com.example.survey.controller;

import com.example.survey.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder().username(request.getUsername())
				.password(request.getPassword())
				.roles("ROLE_USER")
				.build();

		return null;
	}

}
