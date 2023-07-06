package com.example.tour.controller;

import com.example.tour.model.UserModel;
import com.example.tour.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testListUsers() throws Exception {

        UserModel user1 = new UserModel(1L,"Сидоров", "Илья", "Владимирович", "Ярославль", "Средняя школа № 4 имени Н. А. Некрасова", 9,"Б","oge2782@mail.ru","abcabc","+7 734 870 49 88",LocalDate.of(2005,1,12));


        List<UserModel> users = Arrays.asList(user1);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<UserModel> page = new PageImpl<>(users, pageable, users.size());

        when(userService.getall(pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(users.size()))
                .andExpect(jsonPath("$.content[0].id").value(user1.getId()))
                .andExpect(jsonPath("$.content[0].name").value(user1.getName()))
                .andExpect(jsonPath("$.content[0].surname").value(user1.getSurname()))
                .andExpect(jsonPath("$.content[0].patronymic").value(user1.getPatronymic()))
                .andExpect(jsonPath("$.content[0].date_of_birth").value(user1.getDate_of_birth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .andExpect(jsonPath("$.content[0].phone").value(user1.getPhone()))
                .andExpect(jsonPath("$.content[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$.content[0].password").value(user1.getPassword()))
                .andExpect(jsonPath("$.content[0].city").value(user1.getCity()))
                .andExpect(jsonPath("$.content[0].school").value(user1.getSchool()))
                .andExpect(jsonPath("$.content[0].grade_number").value(user1.getGrade_number()))
                .andExpect(jsonPath("$.content[0].grade_letter").value(user1.getGrade_letter()));
        verify(userService, times(1)).getall(any(Pageable.class));
    }

    @Test
    public void testCreateUser() throws Exception {

        UserModel user1 = new UserModel(1L,"Сидоров", "Илья", "Владимирович", "Ярославль", "Средняя школа № 4 имени Н. А. Некрасова", 9,"Б","oge2782@mail.ru","abcabc","+7 734 870 49 88",LocalDate.of(2005,1,12));

        when(userService.create(any(UserModel.class))).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.name").value(user1.getName()))
                .andExpect(jsonPath("$.surname").value(user1.getSurname()))
                .andExpect(jsonPath("$.patronymic").value(user1.getPatronymic()))
                .andExpect(jsonPath("$.date_of_birth").value(user1.getDate_of_birth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .andExpect(jsonPath("$.phone").value(user1.getPhone()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()))
                .andExpect(jsonPath("$.password").value(user1.getPassword()))
                .andExpect(jsonPath("$.city").value(user1.getCity()))
                .andExpect(jsonPath("$.school").value(user1.getSchool()))
                .andExpect(jsonPath("$.grade_number").value(user1.getGrade_number()))
                .andExpect(jsonPath("$.grade_letter").value(user1.getGrade_letter()));
    }

    @Test
    public void testGetUserById() throws Exception {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);

        when(userService.getbyid(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserModel user1 = new UserModel(1L,"Сидоров", "Илья", "Владимирович", "Ярославль", "Средняя школа № 4 имени Н. А. Некрасова", 9,"Б","oge2782@mail.ru","abcabc","+7 734 870 49 88",LocalDate.of(2005,1,12));
        UserModel user2 = new UserModel(2L,"Афанасьев", "Роберт", "Григорьевич", "Москва", "ГБОУ школа №179",8,"А","dan31022@gmail.com","abcabc","+7 223 620 43 80",LocalDate.of(2006,6,18));

        when(userService.getbyid(user1.getId())).thenReturn(Optional.of(user1));
        when(userService.redact(eq(user1.getId()), any(UserModel.class))).thenReturn(user2);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", user1.getId())
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user2.getId()))
                .andExpect(jsonPath("$.name").value(user2.getName()))
                .andExpect(jsonPath("$.surname").value(user2.getSurname()))
                .andExpect(jsonPath("$.patronymic").value(user2.getPatronymic()))
                .andExpect(jsonPath("$.date_of_birth").value(user2.getDate_of_birth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))))
                .andExpect(jsonPath("$.phone").value(user2.getPhone()))
                .andExpect(jsonPath("$.email").value(user2.getEmail()))
                .andExpect(jsonPath("$.password").value(user2.getPassword()))
                .andExpect(jsonPath("$.city").value(user2.getCity()))
                .andExpect(jsonPath("$.school").value(user2.getSchool()))
                .andExpect(jsonPath("$.grade_number").value(user2.getGrade_number()))
                .andExpect(jsonPath("$.grade_letter").value(user2.getGrade_letter()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        when(userService.getbyid(userId)).thenReturn(Optional.of(new UserModel()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).delete(userId);
    }
}
