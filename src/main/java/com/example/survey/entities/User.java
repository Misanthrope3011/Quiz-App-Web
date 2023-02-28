package com.example.survey.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SVY_USERS")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "usr_id")
	private Long id;

	@Column(name = "usr_name")
	private String name;

	@Column(name = "usr_surname")
	private String surname;

	@Column(name = "usr_username")
	private String username;

	@Column(name = "usr_password")
	private String password;

	@Column(name = "usr_points")
	private Long points;

	@Enumerated(value = EnumType.STRING)
	@OneToMany
	@JoinColumn(name = "prv_usr_id", referencedColumnName = "usr_id")
	private List<UserRoles> roles;

	private boolean isExpired = false;
	private boolean isLocked = false;
	private boolean isEnabled = true;
	private boolean isCredentialsExpired = false;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
