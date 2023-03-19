package com.example.survey.mappers;

import com.example.survey.entities.UserEntity;
import com.example.survey.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	UserDTO userToDTO(UserEntity user);

	UserEntity dtoToUser(UserDTO user);

}
