package com.vihanaus.cool_steps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "owners")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String name;
    private String email;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user")
    private List<DailySummary> dailySummaryList;

    @OneToMany(mappedBy = "user")
    private List<Device> deviceList;

}
