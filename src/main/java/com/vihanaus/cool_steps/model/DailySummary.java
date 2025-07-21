package com.vihanaus.cool_steps.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "daily_summaries")
public class DailySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Float steps;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "dailySummary")
    private List<StepEvent> stepEventList;
}
