package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;

public interface UserService {

    User create(UserDTO userDTO) throws UserAlreadyExistException;
}
