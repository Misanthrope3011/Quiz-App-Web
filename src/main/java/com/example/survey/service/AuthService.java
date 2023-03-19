package com.example.survey.service;

import com.example.survey.controller.AuthenticationRequest;
import com.example.survey.controller.RegisterRequest;
import com.example.survey.entities.Roles;
import com.example.survey.entities.UserEntity;
import com.example.survey.entities.UserRoles;
import com.example.survey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AuthenticationResponse register(RegisterRequest request) {
		UserEntity user = UserEntity.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles(Collections.singletonList(new UserRoles(Roles.USER)))
				.build();

		userRepository.save(user);
		String token = jwtService.generateToken(user);

		return buildAuthResponse(token);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(), request.getPassword()
		));

		var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);

		return buildAuthResponse(token);
	}

	private static AuthenticationResponse buildAuthResponse(String token) {
		return AuthenticationResponse
				.builder()
				.token(token)
				.build();
	}

}
