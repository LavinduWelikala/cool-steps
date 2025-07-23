package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<User> findById(Long id);
}
