package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.StepEvent;

import java.util.List;

public interface StepEventService {

    StepEvent create(Long deviceId, StepEventDTO stepEventDTO) throws DeviceNotFoundException;

    List<StepEvent> findAll();

    List<StepEvent> findByAllIByDeviceId(Long deviceId) throws DeviceNotFoundException;
}
