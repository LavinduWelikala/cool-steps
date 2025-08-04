package com.vihanaus.cool_steps.controller.response;

import com.vihanaus.cool_steps.model.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailySummaryResponse {

    private Long dailySummaryId;
    private LocalDate date;
    private Float steps;

    @ManyToOne
    private Long userId;
}
