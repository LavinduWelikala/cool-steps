package com.vihanaus.cool_steps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "iot_devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String serialNumber;
    private String model;
    private String manufacturer;
    private LocalDate RegisteredAt;

    @ManyToOne
    private User user;
}
