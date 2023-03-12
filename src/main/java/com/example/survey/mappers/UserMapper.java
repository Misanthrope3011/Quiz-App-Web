package com.example.survey.mappers;

import com.example.survey.entities.User;
import com.example.survey.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO userToDTO(User user);

	User dtoToUser(UserDTO user);

}
