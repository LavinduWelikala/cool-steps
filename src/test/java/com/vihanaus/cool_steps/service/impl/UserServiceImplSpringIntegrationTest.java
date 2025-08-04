package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.UserRepository;
import com.vihanaus.cool_steps.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplSpringIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;  // Add DeviceRepository here

    @BeforeEach
    void clearDB() {
        // Delete devices first to avoid FK constraint violation
        deviceRepository.deleteAll();

        // Then delete users
        userRepository.deleteAll();
    }

    @Test
    void testCreateUser() throws UserAlreadyExistException {
        UserDTO dto = new UserDTO();
        dto.setUserId("u123");
        dto.setName("Chathuranga");
        dto.setEmail("chathu@example.com");

        User created = userService.create(dto);

        assertNotNull(created.getId());
        assertEquals("u123", created.getUserId());
        assertEquals("Chathuranga", created.getName());
        assertEquals("chathu@example.com", created.getEmail());
    }

    @Test
    void testCreateUserAlreadyExists() throws UserAlreadyExistException {
        UserDTO dto = new UserDTO();
        dto.setUserId("u123");
        dto.setName("Chathuranga");
        dto.setEmail("chathu@example.com");

        userService.create(dto);

        assertThrows(UserAlreadyExistException.class, () -> userService.create(dto));
    }

    @Test
    void testFindByIdSuccess() throws NotFoundException, UserAlreadyExistException {
        UserDTO dto = new UserDTO();
        dto.setUserId("u456");
        dto.setName("Tharindu");
        dto.setEmail("tharindu@example.com");

        User created = userService.create(dto);

        User found = userService.findById(created.getId());
        assertEquals("Tharindu", found.getName());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(NotFoundException.class, () -> userService.findById(999L));
    }

    @Test
    void testUpdateUser() throws NotFoundException, UserAlreadyExistException {
        UserDTO dto = new UserDTO();
        dto.setUserId("u789");
        dto.setName("Original");
        dto.setEmail("original@example.com");

        User created = userService.create(dto);

        UserDTO updated = new UserDTO();
        updated.setUserId("u789");
        updated.setName("Updated");
        updated.setEmail("updated@example.com");

        User result = userService.updateById(created.getId(), updated);

        assertEquals("Updated", result.getName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void testUpdateUserNotFound() {
        UserDTO updated = new UserDTO();
        updated.setUserId("ghost");
        updated.setName("Ghost");
        updated.setEmail("ghost@example.com");

        assertThrows(NotFoundException.class, () -> userService.updateById(888L, updated));
    }

    @Test
    void testFindAllUsers() throws UserAlreadyExistException {
        UserDTO dto1 = new UserDTO();
        dto1.setUserId("u1");
        dto1.setName("User One");
        dto1.setEmail("u1@example.com");

        UserDTO dto2 = new UserDTO();
        dto2.setUserId("u2");
        dto2.setName("User Two");
        dto2.setEmail("u2@example.com");

        userService.create(dto1);
        userService.create(dto2);

        List<User> allUsers = userService.findAll();

        assertEquals(2, allUsers.size());
    }
}
