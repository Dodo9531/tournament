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
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .patronymic(request.getPatronymic())
                .grade_letter(request.getGrade_letter())
                .grade_number(request.getGrade_number())
                .date_of_birth(request.getDate_of_birth())
                .city(request.getCity())
                .school(request.getSchool())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Roles.STUDENT)
                .build();
        if(repo.findByUsername(user.getUsername()).isPresent())
            throw new RuntimeException("User with that username already exist");
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
                        request.getUsername(),
                        request.getPassword()
        ));
    var user = repo.findByUsername(request.getUsername())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse
            .builder()
            .token(jwtToken)
            .build();
    }
}
