package com.example.tour.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
@Schema(description = "User details")
@Entity
public class UserModel {
    @Schema(description = "User Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "User name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(name = "User surname", requiredMode = Schema.RequiredMode.REQUIRED)
    private String surname;
    @Schema(name = "User patronymic", requiredMode = Schema.RequiredMode.REQUIRED)
    private String patronymic;
    @Schema(name = "User city", requiredMode = Schema.RequiredMode.REQUIRED)
    private String city;
    @Schema(name = "User school", requiredMode = Schema.RequiredMode.REQUIRED)
    private String school;
    @Schema(name = "User grade_number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer grade_number;
    @Schema(name = "User grade_letter", requiredMode = Schema.RequiredMode.REQUIRED)
    private String grade_letter;
    @Schema(name = "User email", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email
    private String email;
    @Schema(name = "User password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(name = "User phone")
    private String phone;
    @Schema(name = "User date_of_birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date_of_birth;

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getGrade_number() {
        return grade_number;
    }

    public void setGrade_number(Integer grade_number) {
        this.grade_number = grade_number;
    }
    public String getGrade_letter() {
        return grade_letter;
    }

    public void setGrade_letter(String grade_letter) {
        this.grade_letter = grade_letter;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel(Long id, String name, String lastName, String patronymic, String city , String school, Integer grade_number, String grade_letter, String email, String password,String phone,LocalDate date_of_birth) {
        this.id = id;
        this.name = name;
        this.surname = lastName;
        this.patronymic = patronymic;
        this.city = city;
        this.school = school;
        this.grade_number = grade_number;
        this.grade_letter = grade_letter;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.date_of_birth =date_of_birth;
    }
    public UserModel() {}
}
