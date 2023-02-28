package com.example.survey.entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "SVY_USR_PRIVILEGES")
public class UserRoles implements GrantedAuthority {

	@Id
	@Column(name = "prv_usr_id")
	private Long id;

	@Column(name = "usp_role")
	private Roles role;

	@Override
	public String getAuthority() {
		return role.toString();
	}
}
