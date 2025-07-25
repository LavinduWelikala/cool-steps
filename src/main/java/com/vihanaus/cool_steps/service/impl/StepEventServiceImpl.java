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
    public StepEvent create(Long deviceId) throws DeviceNotFoundException {

        Device existingDevice = deviceRepository.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException("Device with id " + deviceId + " not found"));

        User user = existingDevice.getUser();

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

        Float newSteps = 1f; 
        StepEvent lastEvent = stepEventRepository.findTopByDeviceOrderByTimestampDesc(existingDevice).orElse(null);

        if (lastEvent != null) {
            newSteps = lastEvent.getStepCount() + 1;
        }
        StepEvent stepEvent = new StepEvent();
        stepEvent.setDevice(existingDevice);
        stepEvent.setStepCount(newSteps);
        stepEvent.setTimestamp(now);

        stepEventRepository.save(stepEvent);

        summary.setSteps(summary.getSteps() + newSteps);
        dailySummaryRepository.save(summary);

        return stepEvent;
    }
}
