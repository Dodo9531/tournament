package com.example.tour.controller;

import com.example.tour.dto.UserDTO;
import com.example.tour.entity.Roles;
import com.example.tour.entity.UserEntity;
import com.example.tour.mappers.UserMapper;
import com.example.tour.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
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
        private UserServiceImpl userServiceImpl;
        @InjectMocks
        private UserController userController;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);

            mockMvc = MockMvcBuilders.standaloneSetup(userController).build();


        }

       /* @Test

        public void testListUsers() throws Exception {

            UserEntity user1 = new UserEntity(1L, "da1", "Сидоров", "Илья", "Владимирович", "Ярославль",
                    "Средняя школа № 4 имени Н. А. Некрасова", 9, "Б", "oge2782@mail.ru",
                    "abcabc", "+7 734 870 49 88", LocalDate.of(2005, 12, 1), Roles.ADMIN);

            UserDTO userDTO = UserMapper.INSTANCE.userToDTO(user1);
            List<UserDTO> users = Arrays.asList(userDTO);
            Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
            Page<UserDTO> page = new PageImpl<UserDTO>(users, pageable, users.size());

            when(userServiceImpl.getall(pageable)).thenReturn(page);

            mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content.length()").value(users.size()))
                    .andExpect(jsonPath("$.content[0].id").value(user1.getId()))
                    .andExpect(jsonPath("$.content[0].username").value(user1.getUsername()))
                    .andExpect(jsonPath("$.content[0].name").value(user1.getName()))
                    .andExpect(jsonPath("$.content[0].surname").value(user1.getSurname()))
                    .andExpect(jsonPath("$.content[0].patronymic").value(user1.getPatronymic()))
                    .andExpect(jsonPath("$.content[0].phone").value(user1.getPhone()))
                    .andExpect(jsonPath("$.content[0].email").value(user1.getEmail()))
                    .andExpect(jsonPath("$.content[0].city").value(user1.getCity()))
                    .andExpect(jsonPath("$.content[0].school").value(user1.getSchool()))
                    .andExpect(jsonPath("$.content[0].grade_number").value(user1.getGrade_number()))
                    .andExpect(jsonPath("$.content[0].grade_letter").value(user1.getGrade_letter()))
                    .andExpect(jsonPath("$.content[0].date_of_birth").value(user1.getDate_of_birth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            verify(userServiceImpl, times(1)).getall(any(Pageable.class));
        }*/

       /* @Test
        public void testCreateUser() throws Exception {
            var user1 = UserEntity.builder()
                    .id(1L)
                    .username("da2")
                    .name("Илья")
                    .surname("Сидоров")
                    .patronymic("Владимирович")
                    .grade_letter("Б")
                    .grade_number(9)
                    .date_of_birth(LocalDate.of(2005, 1, 12))
                    .city("Ярославль")
                    .school("Средняя школа № 4 имени Н. А. Некрасова")
                    .email("oge2782@mail.ru")
                    .password("123")
                    .phone("+7 734 870 49 88")
                    .role(Roles.ADMIN)
                    .build();
            when(userServiceImpl.create(any(UserEntity.class))).thenReturn(user1);
            mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                            .content(objectMapper.writeValueAsString(user1))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(user1.getId()))
                    .andExpect(jsonPath("$.username").value(user1.getUsername()))
                    .andExpect(jsonPath("$.name").value(user1.getName()))
                    .andExpect(jsonPath("$.surname").value(user1.getSurname()))
                    .andExpect(jsonPath("$.patronymic").value(user1.getPatronymic()))
                    .andExpect(jsonPath("$.date_of_birth").value(user1.getDate_of_birth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                    .andExpect(jsonPath("$.phone").value(user1.getPhone()))
                    .andExpect(jsonPath("$.email").value(user1.getEmail()))
                    .andExpect(jsonPath("$.password").value(user1.getPassword()))
                    .andExpect(jsonPath("$.city").value(user1.getCity()))
                    .andExpect(jsonPath("$.school").value(user1.getSchool()))
                    .andExpect(jsonPath("$.grade_number").value(user1.getGrade_number()))
                    .andExpect(jsonPath("$.grade_letter").value(user1.getGrade_letter()))
                    .andExpect(jsonPath("$.role").value(user1.getRole()));

        }*/

        @Test
        public void testGetUserById() throws Exception {
            Long userId = 1L;
            UserEntity user = new UserEntity();
            user.setId(userId);
            user.setRole(Roles.STUDENT);
            when(userServiceImpl.getbyid(userId)).thenReturn(Optional.of(user));

            mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(userId));
        }

      /*  @Test
        @WithMockUser(authorities = "ADMIN")
        public void testUpdateUser() throws Exception {
            UserEntity user1 = new UserEntity(1L, "da1", "Сидоров", "Илья", "Владимирович", "Ярославль",
                    "Средняя школа № 4 имени Н. А. Некрасова", 9, "Б", "oge2782@mail.ru",
                    "abcabc", "+7 734 870 49 88", LocalDate.of(2005, 1, 12), Roles.ADMIN);
            UserEntity user2 = new UserEntity(
                    2L, "da2", "Афанасьев", "Роберт", "Григорьевич", "Москва", "ГБОУ школа №179", 8, "А",
                    "dan31022@gmail.com", "abcabc", "+7 223 620 43 80", LocalDate.of(2006, 6, 18), Roles.STUDENT);
            Authentication authentication = new TestingAuthenticationToken("user", "password", "ADMIN");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            when(userServiceImpl.getbyid(user1.getId())).thenReturn(Optional.of(user1));
            when(userServiceImpl.update(eq(user1.getId()), any(UserEntity.class))).thenReturn(user2);
            mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", user1.getId())
                            .content(objectMapper.writeValueAsString(user1))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(user2.getId()))
                    .andExpect(jsonPath("$.role").value(user2.getRole()))
                    .andExpect(jsonPath("$.username").value(user2.getUsername()))
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
*/
        @Test
        public void testDeleteUser() throws Exception {
            Long userId = 1L;

            when(userServiceImpl.getbyid(userId)).thenReturn(Optional.of(new UserEntity()));

            mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(userServiceImpl, times(1)).delete(userId);
        }
    }

