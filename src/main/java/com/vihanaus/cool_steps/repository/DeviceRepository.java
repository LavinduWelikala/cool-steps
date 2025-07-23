package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findBySerialNumber(String serialNumber);
}
