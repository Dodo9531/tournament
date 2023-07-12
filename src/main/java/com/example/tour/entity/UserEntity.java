package com.example.tour.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user entity with user details.
 */

@Schema(description = "User details")
@Table(name="users")
@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Schema(description = "User Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(name = "Username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
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
    @Schema(name = "User email")
    @Email
    private String email;
    @Schema(name = "User password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(name = "User phone")
    private String phone;
    @Schema(name = "User date_of_birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_of_birth;
    @Enumerated(EnumType.STRING)
    @Schema(name ="User role name", requiredMode = Schema.RequiredMode.REQUIRED)
    private Roles role;

@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
