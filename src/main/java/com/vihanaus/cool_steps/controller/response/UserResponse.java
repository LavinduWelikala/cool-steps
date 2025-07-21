package com.vihanaus.cool_steps.controller.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {

    private Long id;
    private String userId;
    private String name;
    private String email;
    private LocalDate createdAt;
}
