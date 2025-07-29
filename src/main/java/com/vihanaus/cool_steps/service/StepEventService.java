package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.StepEvent;

public interface StepEventService {

    StepEvent create(Long deviceId) throws DeviceNotFoundException;

    StepEvent findById(Long deviceId) throws DeviceNotFoundException;
}
