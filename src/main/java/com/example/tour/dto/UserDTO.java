package com.example.tour.dto;

import com.example.tour.entity.Roles;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserDTO {
    @Id
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String patronymic;
    private String city;
    private String school;
    private Integer grade_number;
    private String grade_letter;
    @Email
    private String email;
    private String phone;
    private LocalDate date_of_birth;
    private Roles role;
}
