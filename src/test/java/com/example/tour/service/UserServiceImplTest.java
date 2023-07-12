package com.example.tour.service;

import com.example.tour.entity.Roles;
import com.example.tour.entity.UserEntity;
import com.example.tour.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ValidUserEntity_ReturnsCreatedUserEntity() {

        UserEntity userEntity = UserEntity.builder()
                .username("testUser")
                .name("Da")
                .surname("Da")
                .email("Da@example.com")
                .password("123")
                .role(Roles.ADMIN)
                .build();

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn(userEntity.getPassword());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);


        UserEntity createdUser = userService.create(userEntity);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("Da", createdUser.getName());
        assertEquals("Da", createdUser.getSurname());
        assertEquals("Da@example.com", createdUser.getEmail());
        assertEquals("123", createdUser.getPassword());
        assertEquals(Roles.ADMIN, createdUser.getRole());

        verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
        verify(userRepository, times(1)).findByUsername(userEntity.getUsername());
        verify(passwordEncoder, times(1)).encode(userEntity.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }


    @Test
    void createUser_UsernameOrEmailAlreadyExists_ThrowsIllegalArgumentException() {

        UserEntity userEntity = UserEntity.builder()
                .username("testUser")
                .email("test@example.com")
                .build();

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));


        assertThrows(IllegalArgumentException.class, () -> userService.create(userEntity));

        verify(userRepository, times(1)).findByEmail(userEntity.getEmail());
        verify(userRepository, times(1)).findByUsername(userEntity.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
    @Test
    void updateUser_UserExists_UpdatesUserEntity() {

        Long userId = 1L;
        UserEntity existingUserEntity = UserEntity.builder()
                .id(userId)
                .name("Da")
                .surname("Da")
                .build();
        UserEntity updatedUserEntity = UserEntity.builder()
                .id(userId)
                .name("Da1")
                .surname("Da1")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        UserEntity result = userService.update(userId, updatedUserEntity);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Da1", result.getName());
        assertEquals("Da1", result.getSurname());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void updateUser_UserDoesNotExist_ReturnsNull() {

        Long userId = 1L;
        UserEntity updatedUserEntity = UserEntity.builder()
                .name("Updated Name")
                .surname("Updated Surname")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        UserEntity result = userService.update(userId, updatedUserEntity);


        assertNull(result);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
    @Test
    void getById_UserExists_ReturnsOptionalOfUserEntity() {

        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));


        Optional<UserEntity> result = userService.getbyid(userId);

        assertTrue(result.isPresent());
        assertEquals(userEntity, result.get());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getById_UserDoesNotExist_ReturnsEmptyOptional() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        Optional<UserEntity> result = userService.getbyid(userId);

        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void delete_UserExists_DeletesUserEntity() {
        Long userId = 1L;

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
