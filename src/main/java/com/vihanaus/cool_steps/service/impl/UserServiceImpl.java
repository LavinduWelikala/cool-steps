package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.UserDTO;
import com.vihanaus.cool_steps.exception.NotFoundException;
import com.vihanaus.cool_steps.exception.UserAlreadyExistException;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.UserRepository;
import com.vihanaus.cool_steps.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User create(UserDTO userDTO) throws UserAlreadyExistException {

        Optional<User> existingUser = userRepository.findByUserId(userDTO.getUserId());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException("Owner ID" + userDTO.getUserId() + " already exists");
        }

        LocalDate createdAt = LocalDate.now();

        User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCreatedAt(createdAt);

        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws NotFoundException {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        return existingUser;
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User updateById(Long id, UserDTO userDTO) throws NotFoundException {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));

        LocalDate updatedAt = LocalDate.now();

        User updateUser = new User();

        updateUser.setId(existingUser.getId());
        updateUser.setUserId(userDTO.getUserId());
        updateUser.setName(userDTO.getName());
        updateUser.setEmail(userDTO.getEmail());
        updateUser.setCreatedAt(updatedAt);

        return userRepository.save(updateUser);
    }


}
