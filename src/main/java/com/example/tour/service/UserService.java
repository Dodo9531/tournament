package com.example.tour.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService<T> {
   T create(T t);
   Page<T> getall(Pageable pageable);
   Optional<T> getbyid(Long id);
   void delete(Long id);
   T update(Long id, T t);
}
