package com.example.tour.service;
import com.example.tour.entity.AuthenticationRequest;
import com.example.tour.entity.AuthenticationResponse;
import com.example.tour.entity.RegisterRequest;
import com.example.tour.entity.Roles;
import com.example.tour.entity.UserEntity;
import com.example.tour.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request)
    {
        var user = UserEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .patronymic(request.getPatronymic())
                .grade_letter(request.getGrade_letter())
                .grade_number(request.getGrade_number())
                .dateOfBirth(request.getDateOfBirth())
                .city(request.getCity())
                .school(request.getSchool())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Roles.STUDENT)
                .build();
        if(repo.findByEmail(user.getEmail()).isPresent())
            throw new RuntimeException("User with that email already exist");
        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
        ));
    var user = repo.findByEmail(request.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse
            .builder()
            .token(jwtToken)
            .build();
    }
}
