package com.example.tour.controller;

import com.example.tour.entity.AuthenticationRequest;
import com.example.tour.entity.AuthenticationResponse;
import com.example.tour.entity.RegisterRequest;
import com.example.tour.entity.UserEntity;
import com.example.tour.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController{
    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {
        return  ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request)
    {
        return  ResponseEntity.ok(service.login(request));
    }
    @PostMapping("/pass")
    public String passhash(UserEntity userEntity)
    {
        return passwordEncoder.encode("123");
    }

}
