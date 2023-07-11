package com.example.tour.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String role;
    private String patronymic;
    private String city;
    private String school;
    private Integer grade_number;
    private String grade_letter;
    private String phone;
    private LocalDate dateOfBirth;
}
