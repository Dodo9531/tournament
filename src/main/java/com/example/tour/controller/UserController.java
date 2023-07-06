package com.example.tour.controller;

import com.example.tour.model.UserModel;
import com.example.tour.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Manages operations with users")
public class UserController {
    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }
    @Operation(summary = "Get all users", description = "Returns all users", method = "GET")
    @GetMapping
    public Page<UserModel> list(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer size,
                                @RequestParam(defaultValue = "id") String sort)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return service.getall(pageable);
    }
    @Operation(summary = "Create user", description = "Create user with given parameters")
    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel userModel)
    {
        return service.create(userModel);
    }
    @Operation(summary = "Get member by ID", description = "Returns a single member by their ID")
    @GetMapping("/{id}")
    public UserModel getbyid(@PathVariable Long id)
    {
        if(service.getbyid(id).isPresent()) return service.getbyid(id).get();
        else return null;
    }
    @Operation(summary = "Update user by ID", description = "Update a single user by their ID")
    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id,@RequestBody UserModel userModel)
    {
            if(service.getbyid(id).isPresent()) return service.redact(id,userModel);
            else return null;
    }
    @Operation(summary = "Delete user by ID", description = "Delete a single user by their ID")
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id)
    {
        if(service.getbyid(id).isPresent())  service.delete(id);
    }
}
