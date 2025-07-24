package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.DeviceDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.exception.DuplicateDeviceFoundException;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.UserRepository;
import com.vihanaus.cool_steps.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private UserRepository userRepository;

    @Override
    public Device create(Long userId, DeviceDTO deviceDTO) throws DeviceNotFoundException {

        Optional<User> newUser = userRepository.findById(userId);
        if (!newUser.isPresent()) {
            throw new DeviceNotFoundException("User with id " + userId + " not found");
        }

        User setUser = newUser.get();
        LocalDate registeredAt = LocalDate.now();

        Device device = new Device();

        device.setName(deviceDTO.getName());
        device.setSerialNumber(deviceDTO.getSerialNumber());
        device.setModel(deviceDTO.getModel());
        device.setManufacturer(deviceDTO.getManufacturer());
        device.setRegisteredAt(registeredAt);
        device.setUser(setUser);

        return deviceRepository.save(device);
    }

    @Override
    public List<Device> findAll() {

        return deviceRepository.findAll();
    }

    @Override
    public Device findById(Long deviceId) throws DeviceNotFoundException {

        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException("Device with id " + deviceId + " not found"));

        return device;
    }
}
