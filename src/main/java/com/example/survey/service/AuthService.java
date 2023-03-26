package com.example.survey.service;

import com.example.survey.controller.AuthenticationRequest;
import com.example.survey.controller.RegisterRequest;
import com.example.survey.dto.UserDTO;
import com.example.survey.entities.Roles;
import com.example.survey.entities.UserEntity;
import com.example.survey.entities.UserRoles;
import com.example.survey.mappers.UserMapper;
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

	public UserDTO register(RegisterRequest request) {
		UserEntity user = UserEntity.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.isEnabled(Boolean.TRUE)
				.roles(Collections.singletonList(new UserRoles(Roles.USER)))
				.build();

		userRepository.save(user);
		String token = jwtService.generateToken(user);

		return buildAuthResponse(token, user);
	}

	public UserDTO authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(), request.getPassword()
		));

		var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user);

		return buildAuthResponse(token, user);
	}

	private static UserDTO buildAuthResponse(String token, UserEntity entity) {
		UserDTO userDTO = UserMapper.INSTANCE.userToDTO(entity);
		userDTO.setToken(token);
		userDTO.setUserRoles(entity.getRoles());

		return userDTO;
	}

}
