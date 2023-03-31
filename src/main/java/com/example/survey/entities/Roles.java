package com.example.survey.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

	ADMIN,
	USER,
	MOD;

	@Override
	public String getAuthority() {
		return name();
	}

}
