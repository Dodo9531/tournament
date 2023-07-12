package com.example.tour.service;

import com.example.tour.dto.UserDTO;
import com.example.tour.entity.Roles;
import com.example.tour.entity.UserEntity;
import com.example.tour.mappers.UserMapper;
import com.example.tour.repository.UserRepository;
import org.hibernate.query.ResultListTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
            user = repo.findByUsername(userEntity.getUsername());
            if (user.isPresent())
                throw  new IllegalArgumentException("Username taken");
        var userToCreate = UserEntity.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .patronymic(userEntity.getPatronymic())
                .grade_letter(userEntity.getGrade_letter())
                .grade_number(userEntity.getGrade_number())
                .date_of_birth(userEntity.getDate_of_birth())
                .city(userEntity.getCity())
                .school(userEntity.getSchool())
                .email(userEntity.getEmail())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .build();
       return repo.save(userToCreate);
    }

    @Override
    public Page<UserDTO> getall(Pageable pageable) {

        return new PageImpl<UserDTO>(UserMapper.INSTANCE.usersToDTOs(repo.findAll(pageable).getContent()));
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
            if(userEntity.getDate_of_birth()!=null) user.setDate_of_birth(userEntity.getDate_of_birth());
            return repo.save(user);
        }
        else  return null;
    }


}
