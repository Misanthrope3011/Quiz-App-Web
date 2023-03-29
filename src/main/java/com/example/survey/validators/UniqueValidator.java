package com.example.survey.validators;

import com.example.survey.pojo.RegisterRequest;
import com.example.survey.entities.UserEntity;
import com.example.survey.repository.UserRepository;
import com.example.survey.validators.annotation.UniqueUser;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<UniqueUser, RegisterRequest> {

	private final UserRepository userRepository;

	@Override
	public boolean isValid(RegisterRequest request, ConstraintValidatorContext constraintValidatorContext) {
		Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());

		 if(userEntity.isPresent()) {
			constraintValidatorContext.buildConstraintViolationWithTemplate("User already exists")
					.addConstraintViolation();

			return false;
		}
		return true;
	}

}
