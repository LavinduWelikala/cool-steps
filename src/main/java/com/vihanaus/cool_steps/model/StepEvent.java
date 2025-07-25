package com.vihanaus.cool_steps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class StepEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private Float stepCount;

    @ManyToOne
    private DailySummary dailySummary;

    @ManyToOne
    private Device device;

}
