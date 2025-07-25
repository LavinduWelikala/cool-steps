package com.vihanaus.cool_steps.controller.response;

import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.model.Device;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StepEventResponse {

    private Long stepEventId;
    private LocalDateTime timestamp;
    private Float stepCount;
    private Long deviceId;
}
