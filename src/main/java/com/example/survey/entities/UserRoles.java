package com.example.survey.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SVY_USR_PRIVILEGES")
public class UserRoles implements GrantedAuthority {

	@Id
	@Column(name = "usp_usr_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "usp_role")
	private Roles role;

	@Override
	public String getAuthority() {
		return role.toString();
	}

	public UserRoles(Roles role) {
		this.role = role;
	}

}
