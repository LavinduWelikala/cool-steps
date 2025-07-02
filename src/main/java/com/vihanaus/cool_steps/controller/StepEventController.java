package com.vihanaus.cool_steps.controller;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.controller.response.StepEventResponse;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.StepEvent;
import com.vihanaus.cool_steps.service.StepEventService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class StepEventController {

    private StepEventService stepEventService;

    @PostMapping(value = "/events")
    public StepEventResponse createEvent(@RequestBody @Validated StepEventDTO stepEventDTO, @RequestParam("device-id") Long deviceId) throws DeviceNotFoundException {

        StepEvent stepEvent = stepEventService.create(deviceId, stepEventDTO);

        StepEventResponse stepEventResponse = new StepEventResponse();
        stepEventResponse.setStepEventId(stepEvent.getId());
        stepEventResponse.setStepCount(stepEvent.getStepCount());
        stepEventResponse.setTimestamp(stepEvent.getTimestamp());
        stepEventResponse.setDeviceId(stepEvent.getDevice().getId());

        return stepEventResponse;
    }
    
    @GetMapping(value = "/events")
    public List<StepEventResponse> getAll()  {

        List<StepEvent> stepEvents = stepEventService.findAll();

        List<StepEventResponse> stepEventResponses = new ArrayList<>();

        for (StepEvent stepEvent : stepEvents) {
            StepEventResponse stepEventResponse = new StepEventResponse();
            stepEventResponse.setStepEventId(stepEvent.getId());
            stepEventResponse.setStepCount(stepEvent.getStepCount());
            stepEventResponse.setTimestamp(stepEvent.getTimestamp());
            stepEventResponse.setDeviceId(stepEvent.getDevice().getId());
            stepEventResponses.add(stepEventResponse);
        }
        return stepEventResponses;
    }

    @GetMapping(value = "/events/{device-id}")
    public List<StepEventResponse> getAllByDeviceId(@PathVariable("device-id") Long deviceId)  {

        List<StepEvent> stepEvents = stepEventService.findByAllIByDeviceId(deviceId);

        List<StepEventResponse> stepEventResponses = new ArrayList<>();

        for (StepEvent stepEvent : stepEvents) {
            StepEventResponse stepEventResponse = new StepEventResponse();
            stepEventResponse.setStepEventId(stepEvent.getId());
            stepEventResponse.setStepCount(stepEvent.getStepCount());
            stepEventResponse.setTimestamp(stepEvent.getTimestamp());
            stepEventResponse.setDeviceId(stepEvent.getDevice().getId());
            stepEventResponses.add(stepEventResponse);
        }
        return stepEventResponses;
    }
}
