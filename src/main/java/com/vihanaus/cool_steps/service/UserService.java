package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;

import java.util.List;

public interface UserService {

    User create(UserDTO userDTO) throws UserAlreadyExistException;

    User findById(Long id) throws NotFoundException;

    List<User> findAll();

    User updateById(Long id, UserDTO userDTO) throws NotFoundException;
}
