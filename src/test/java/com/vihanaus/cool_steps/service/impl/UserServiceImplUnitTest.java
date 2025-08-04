package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("create user - success")
    void testCreateUserSuccess() throws Exception {
        String userId = "u123";
        String name = "Jane";
        String email = "jane@example.com";

        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setName(name);
        dto.setEmail(email);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUserId(userId);
        savedUser.setName(name);
        savedUser.setEmail(email);
        savedUser.setCreatedAt(LocalDate.now());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        var result = userService.create(dto);

        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getUserId(), result.getUserId());
        assertEquals(savedUser.getName(), result.getName());
        assertEquals(savedUser.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("create user - user already exists")
    void testCreateUserAlreadyExists() {
        String userId = "u123";

        User existingUser = new User();
        existingUser.setUserId(userId);

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existingUser));

        UserDTO dto = new UserDTO();
        dto.setUserId(userId);
        dto.setName("Jane");
        dto.setEmail("jane@example.com");

        assertThrows(UserAlreadyExistException.class, () -> userService.create(dto));
    }

    @Test
    @DisplayName("find user by id - success")
    void testFindByIdSuccess() throws Exception {
        Long id = 1L;

        User user = new User();
        user.setId(id);
        user.setUserId("u123");
        user.setName("Jane");
        user.setEmail("jane@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        var result = userService.findById(id);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    @DisplayName("find user by id - not found")
    void testFindByIdNotFound() {
        Long id = 999L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(id));
    }

    @Test
    @DisplayName("update user - success")
    void testUpdateUserSuccess() throws Exception {
        Long id = 1L;

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setUserId("u123");
        existingUser.setName("Old Name");
        existingUser.setEmail("old@example.com");

        UserDTO updateDto = new UserDTO();
        updateDto.setUserId("u123");
        updateDto.setName("New Name");
        updateDto.setEmail("new@example.com");

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setUserId("u123");
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");
        updatedUser.setCreatedAt(LocalDate.now());

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        var result = userService.updateById(id, updateDto);

        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("update user - not found")
    void testUpdateUserNotFound() {
        Long id = 999L;

        UserDTO updateDto = new UserDTO();
        updateDto.setUserId("u999");
        updateDto.setName("Ghost");
        updateDto.setEmail("ghost@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateById(id, updateDto));
    }

    @Test
    @DisplayName("find all users")
    void testFindAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUserId("u123");

        User user2 = new User();
        user2.setId(2L);
        user2.setUserId("u456");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        var result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("u123", result.get(0).getUserId());
    }
}
