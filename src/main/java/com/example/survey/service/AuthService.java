package com.example.survey.service;

import com.example.survey.response.AuthenticationRequest;
import com.example.survey.pojo.RegisterRequest;
import com.example.survey.pojo.UserDTO;
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

	public static final long TOKEN_VALIDITY_TIME = 1000 * 60 * 15;
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
		String accessToken = jwtService.generateToken(user,TOKEN_VALIDITY_TIME);

		return buildAuthResponse(null, accessToken, user);
	}

	public UserDTO authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(), request.getPassword()
		));

		var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.generateToken(user, TOKEN_VALIDITY_TIME);
		String refreshToken = jwtService.generateToken(user, TOKEN_VALIDITY_TIME * 10);

		return buildAuthResponse(refreshToken, token, user);
	}

	private static UserDTO buildAuthResponse(String refreshToken, String accessToken, UserEntity entity) {
		UserDTO userDTO = UserMapper.INSTANCE.userToDTO(entity);
		userDTO.setAccessToken(accessToken);
		userDTO.setRefreshToken(refreshToken);
		userDTO.setUserRoles(entity.getRoles());

		return userDTO;
	}

}
