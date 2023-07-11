package com.example.tour.service;

import com.example.tour.entity.UserEntity;
import com.example.tour.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<UserEntity>{
private final UserRepository repo;
private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity create(UserEntity userEntity) throws IllegalArgumentException {
            Optional<UserEntity> user = repo.findByEmail(userEntity.getEmail());
            if (user.isPresent())
          throw  new IllegalArgumentException("Email taken");
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
       return repo.save(userEntity);
    }

    @Override
    public Page<UserEntity> getall(Pageable pageable) {
        return repo.findAll(pageable);
    }
    @Override
    public Optional<UserEntity> getbyid(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
    repo.deleteById(id);
    }


    @Override
    public UserEntity update(Long id, UserEntity userEntity) {
        Optional<UserEntity> useroptional = repo.findById(id);
        if(useroptional.isPresent())
        {
            UserEntity user = useroptional.get();
            if(userEntity.getName()!=null) user.setName(userEntity.getName());
            if(userEntity.getSurname()!=null) user.setSurname(userEntity.getSurname());
            if(userEntity.getPatronymic()!=null) user.setPatronymic(userEntity.getPatronymic());
            if(userEntity.getCity()!=null) user.setCity(userEntity.getCity());
            if(userEntity.getSchool()!=null) user.setSchool(userEntity.getSchool());
            if(userEntity.getGrade_number()!=null) user.setGrade_number(userEntity.getGrade_number());
            if(userEntity.getGrade_letter()!=null) user.setGrade_letter(userEntity.getGrade_letter());
            if(userEntity.getEmail()!=null) user.setEmail(userEntity.getEmail());
            if(userEntity.getPassword()!=null) user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            if(userEntity.getPhone()!=null) user.setPhone(userEntity.getPhone());
            if(userEntity.getDateOfBirth()!=null) user.setDateOfBirth(userEntity.getDateOfBirth());
            return repo.save(user);
        }
        else  return null;
    }


}
