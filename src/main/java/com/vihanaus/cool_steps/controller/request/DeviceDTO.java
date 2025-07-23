package com.vihanaus.cool_steps.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceDTO {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Serial number must not be blank")
    @Size(max = 50, message = "Serial number must be at most 50 characters")
    private String serialNumber;

    @NotBlank(message = "Model must not be blank")
    @Size(max = 50, message = "Model must be at most 50 characters")
    private String model;

    @NotBlank(message = "Manufacturer must not be blank")
    @Size(max = 100, message = "Manufacturer must be at most 100 characters")
    private String manufacturer;

    private Long userId;
}
