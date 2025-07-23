package com.vihanaus.cool_steps.controller;

import com.vihanaus.cool_steps.controller.request.DeviceDTO;
import com.vihanaus.cool_steps.controller.response.DeviceResponse;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.exception.DuplicateDeviceFoundException;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeviceController {

    private DeviceService deviceService;

    @PostMapping(value = "/users/{user-id}/devices")
    public DeviceResponse create(@PathVariable("user-id") Long userId, @RequestBody @Validated DeviceDTO deviceDTO) throws DuplicateDeviceFoundException, DeviceNotFoundException {

        Device device = deviceService.create(userId, deviceDTO);

        DeviceResponse deviceResponse = new DeviceResponse();

        deviceResponse.setId(device.getId());
        deviceResponse.setName(device.getName());
        deviceResponse.setSerialNumber(device.getSerialNumber());
        deviceResponse.setModel(device.getModel());
        deviceResponse.setManufacturer(device.getManufacturer());
        deviceResponse.setRegisteredAt(device.getRegisteredAt());
        deviceResponse.setUserId(device.getUser().getId());

        return deviceResponse;
    }
}
