package com.vihanaus.cool_steps.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    @NotBlank(message = "Owner ID must not be blank")
    private String userId;

    @NotBlank(message = "Email must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @PastOrPresent(message = "Created date cannot be in the future")
    private LocalDate createdAt;
}
