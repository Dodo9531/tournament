package com.example.tour.mappers;

import com.example.tour.dto.UserDTO;
import com.example.tour.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper{
    @Override
    public UserDTO userToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setSurname(userEntity.getSurname());
        userDTO.setPatronymic(userEntity.getPatronymic());
        userDTO.setCity(userEntity.getCity());
        userDTO.setSchool(userEntity.getSchool());
        userDTO.setGrade_number(userEntity.getGrade_number());
        userDTO.setGrade_letter(userEntity.getGrade_letter());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setRole(userEntity.getRole());
        userDTO.setDate_of_birth(userEntity.getDate_of_birth());
        return userDTO;
    }

    @Override
    public List<UserDTO> usersToDTOs(List<UserEntity> userEntity) {
        if (userEntity==null)
        return null;
        List<UserDTO> list = new ArrayList<UserDTO>(userEntity.size());
        for(UserEntity userEntity1: userEntity)
            list.add(userToDTO(userEntity1));
        return list;
    }


}
