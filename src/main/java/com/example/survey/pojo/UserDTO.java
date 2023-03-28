package com.example.survey.pojo;

import com.example.survey.entities.UserRoles;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

	private String username;
	private String lastName;
	private String email;
	private String surname;
	private String token;
	private List<UserRoles> userRoles;

}
