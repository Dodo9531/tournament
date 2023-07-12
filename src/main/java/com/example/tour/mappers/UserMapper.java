package com.example.tour.mappers;

import com.example.tour.dto.UserDTO;
import com.example.tour.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToDTO(UserEntity userEntity);
    List<UserDTO> usersToDTOs(List<UserEntity> userEntity);

}
