package com.example.tour.service;

import com.example.tour.dto.UserDTO;
import com.example.tour.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService<T> {
   UserEntity create(UserEntity userEntity);
   Page<UserDTO> getall(Pageable pageable);
   Optional<T> getbyid(Long id);
   void delete(Long id);
   T update(Long id, T t);
}
