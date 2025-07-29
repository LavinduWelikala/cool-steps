package com.vihanaus.cool_steps.controller;

import com.vihanaus.cool_steps.controller.response.StepEventResponse;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.StepEvent;
import com.vihanaus.cool_steps.service.StepEventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StepEventController {

    private StepEventService stepEventService;

    @PostMapping(value = "/events")
    public StepEventResponse createEvent(@RequestParam("device-id") Long deviceId) throws DeviceNotFoundException {

        StepEvent stepEvent = stepEventService.create(deviceId);

        StepEventResponse stepEventResponse = new StepEventResponse();
        stepEventResponse.setStepEventId(stepEvent.getId());
        stepEventResponse.setStepCount(stepEvent.getStepCount());
        stepEventResponse.setTimestamp(stepEvent.getTimestamp());
        stepEventResponse.setDeviceId(stepEvent.getDevice().getId());

        return stepEventResponse;
    }
    
    @GetMapping(value = "/events/{device-id}")
    public StepEventResponse getById(@PathVariable("device-id") Long deviceId) throws DeviceNotFoundException {

        StepEvent stepEvent = stepEventService.findById(deviceId);

        StepEventResponse stepEventResponse = new StepEventResponse();
        stepEventResponse.setStepEventId(stepEvent.getId());
        stepEventResponse.setStepCount(stepEvent.getStepCount());
        stepEventResponse.setTimestamp(stepEvent.getTimestamp());
        stepEventResponse.setDeviceId(stepEvent.getDevice().getId());

        return stepEventResponse;
    }
}
