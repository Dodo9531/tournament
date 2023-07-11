package com.example.tour.controller;

import com.example.tour.entity.UserEntity;
import com.example.tour.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * This class represents a controller for managing operations with users.
 */

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manages operations with users")
public class UserController {

    @Autowired
    private final UserService service;


    public UserController(UserService service) {
        this.service = service;
    }
    @Operation(summary = "Get all users", description = "Returns all users", method = "GET")
    @GetMapping
    public Page<UserEntity> list(
            @Parameter(name = "page", description = "Page", required = true) @RequestParam(defaultValue = "0") Integer page,
            @Parameter(name = "size", description = "Page size", required = true) @RequestParam(defaultValue = "10") Integer size,
            @Parameter(name = "sort", description = "Page sort", required = true) @RequestParam(defaultValue = "id") String sort)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return service.getall(pageable);
    }
    @Operation(summary = "Create user", description = "Create user with given parameters")
    @PostMapping("/create")
    public UserEntity create(@Parameter(name = "user", description = "user object", required = true) @RequestBody UserEntity userEntity)
    {
        return (UserEntity) service.create(userEntity);
    }
    @Operation(summary = "Get member by ID", description = "Returns a single member by their ID")
    @GetMapping("/{id}")
    public UserEntity getbyid(@Parameter(name = "id", description = "user id", required = true) @PathVariable Long id)
    {
        if(service.getbyid(id).isPresent()) return (UserEntity) service.getbyid(id).get();
        else return null;
    }

    @Operation(summary = "Update user by ID", description = "Update a single user by their ID")
    @PutMapping("/{id}")
    public UserEntity update(@Parameter(name = "id", description = "user id", required = true)@PathVariable Long id, @Parameter(name = "user", description = "user object", required = true)@RequestBody UserEntity userEntity)
    {
            if(service.getbyid(id).isPresent()) return (UserEntity) service.update(id, userEntity);
            else return null;
    }
    @Operation(summary = "Delete user by ID", description = "Delete a single user by their ID")
    @DeleteMapping("/{id}")
    public void delete (@Parameter(name = "id", description = "user id", required = true)@PathVariable Long id)
    {
        if(service.getbyid(id).isPresent())  service.delete(id);
    }
}
