package com.example.survey.pojo;

import com.example.survey.entities.UserRoles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

	private String username;
	private String lastName;
	private String email;
	private String surname;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String accessToken;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String refreshToken;

	private List<UserRoles> userRoles;

}
