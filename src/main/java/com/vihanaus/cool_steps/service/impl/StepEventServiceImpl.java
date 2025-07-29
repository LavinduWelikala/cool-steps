package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.StepEvent;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DailySummaryRepository;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.StepEventRepository;
import com.vihanaus.cool_steps.service.StepEventService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class StepEventServiceImpl implements StepEventService {

    private StepEventRepository stepEventRepository;
    private DeviceRepository deviceRepository;
    private DailySummaryRepository dailySummaryRepository;

    @Override
    @Transactional
    public StepEvent create(Long deviceId, StepEventDTO stepEventDTO) throws DeviceNotFoundException {

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device with ID " + deviceId + " not found"));

        User user = device.getUser();

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        DailySummary summary = dailySummaryRepository.findByUserAndDate(user, today)
                .orElseGet(() -> {
                    DailySummary newSummary = new DailySummary();
                    newSummary.setUser(user);
                    newSummary.setDate(today);
                    newSummary.setSteps(0f);
                    return dailySummaryRepository.save(newSummary);
                });

        StepEvent stepEvent = new StepEvent();
        stepEvent.setDevice(device);
        stepEvent.setTimestamp(now);
        stepEvent.setStepCount(stepEventDTO.getStepCount());
        stepEvent.setDailySummary(summary);

        stepEventRepository.save(stepEvent);

        Float updatedSteps = summary.getSteps() + stepEventDTO.getStepCount();
        summary.setSteps(updatedSteps);
        dailySummaryRepository.save(summary);

        return stepEvent;
    }

    @Override
    public StepEvent findById(Long deviceId) throws DeviceNotFoundException {

        StepEvent stepEvent = stepEventRepository.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException("Device with id " + deviceId + " not found"));

        return stepEvent;
    }
}
