package com.example.survey.mappers;

import com.example.survey.entities.UserEntity;
import com.example.survey.pojo.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@BeanMapping(ignoreByDefault = true)
	@Mapping(source = "username", target = "username")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "surname", target = "surname")
	@Mapping(source = "roles", target = "userRoles")
	UserDTO userToDTO(UserEntity user);

	UserEntity dtoToUser(UserDTO user);

}
