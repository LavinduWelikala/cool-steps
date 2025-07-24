package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.controller.request.DeviceDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.exception.DuplicateDeviceFoundException;
import com.vihanaus.cool_steps.model.Device;

import java.util.List;

public interface DeviceService {

    Device create(Long userId, DeviceDTO deviceDTO) throws DuplicateDeviceFoundException, DeviceNotFoundException;

    List<Device> findAll();

    Device findById(Long deviceId) throws DeviceNotFoundException;

    Device updateById(Long deviceId, DeviceDTO deviceDTO) throws DeviceNotFoundException;

    void deleteById(Long deviceId);
}
