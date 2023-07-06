package com.example.tour.service;

import com.example.tour.model.UserModel;
import com.example.tour.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements CrudService<UserModel>{
private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserModel create(UserModel userModel) {
        return repo.save(userModel);
    }

    @Override
    public Page<UserModel> getall(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<UserModel> getbyid(Long id) {
        return repo.findById(id);
    }

    @Override
    public void delete(Long id) {
    repo.deleteById(id);
    }

    @Override
    public UserModel redact(Long id, UserModel userModel) {
        Optional<UserModel> useroptional = repo.findById(id);
        if(useroptional.isPresent())
        {
            UserModel user = useroptional.get();
            if(userModel.getName()!=null) user.setName(userModel.getName());
            if(userModel.getSurname()!=null) user.setSurname(userModel.getSurname());
            if(userModel.getPatronymic()!=null) user.setPatronymic(userModel.getPatronymic());
            if(userModel.getCity()!=null) user.setCity(userModel.getCity());
            if(userModel.getSchool()!=null) user.setSchool(userModel.getSchool());
            if(userModel.getGrade_number()!=null) user.setGrade_number(userModel.getGrade_number());
            if(userModel.getGrade_letter()!=null) user.setGrade_letter(userModel.getGrade_letter());
            if(userModel.getEmail()!=null) user.setEmail(userModel.getEmail());
            if(userModel.getPassword()!=null) user.setPassword(userModel.getPassword());
            return repo.save(user);
        }
        else  return null;
    }
}
