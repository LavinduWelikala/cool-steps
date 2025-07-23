package com.vihanaus.cool_steps.controller.response;

import com.vihanaus.cool_steps.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DeviceResponse {

    private Long id;
    private String name;
    private String serialNumber;
    private String model;
    private String manufacturer;
    private LocalDate RegisteredAt;
    private Long userId;
}
