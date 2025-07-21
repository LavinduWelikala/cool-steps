package com.vihanaus.cool_steps.controller;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.controller.response.UserResponse;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(value = "/users")
    public UserResponse create(@RequestBody @Validated UserDTO userDTO) throws UserAlreadyExistException {

        User user = userService.create(userDTO);

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());

        return userResponse;
    }

    @GetMapping(value = "/users/{id}")
    public UserResponse getById(@PathVariable Long id) throws NotFoundException {

        User user = userService.findById(id);

        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());

        return userResponse;    

    }
}
