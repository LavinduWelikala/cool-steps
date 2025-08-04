package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.DeviceDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.exception.DuplicateDeviceFoundException;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.UserRepository;
import com.vihanaus.cool_steps.service.DeviceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeviceServiceImplSpringIntegrationTest {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll(); // child table
        userRepository.deleteAll();   // parent table

        User user = new User();
        user.setUserId("test_user");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        savedUser = user;
    }

    @Test
    @Order(1)
    void testCreateDevice() throws DeviceNotFoundException, DuplicateDeviceFoundException {
        DeviceDTO dto = new DeviceDTO();
        dto.setName("Device1");
        dto.setSerialNumber("ABC123");
        dto.setModel("ModelX");
        dto.setManufacturer("MakerCorp");

        Device created = deviceService.create(savedUser.getId(), dto);

        assertNotNull(created.getId());
        assertEquals("Device1", created.getName());
        assertEquals(savedUser.getId(), created.getUser().getId());
    }

    @Test
    @Order(2)
    void testFindDeviceById() throws DeviceNotFoundException {
        Device device = new Device();
        device.setName("Device2");
        device.setSerialNumber("XYZ999");
        device.setModel("ModelY");
        device.setManufacturer("TestInc");
        device.setUser(savedUser);
        device = deviceRepository.save(device);

        Device found = deviceService.findById(device.getId());

        assertEquals(device.getId(), found.getId());
        assertEquals("Device2", found.getName());
    }

    @Test
    @Order(3)
    void testFindAllDevices() {
        Device device = new Device();
        device.setName("Device3");
        device.setSerialNumber("SN789");
        device.setModel("ZModel");
        device.setManufacturer("AlphaTech");
        device.setUser(savedUser);
        deviceRepository.save(device);

        List<Device> allDevices = deviceService.findAll();

        assertFalse(allDevices.isEmpty());
        assertTrue(allDevices.stream().anyMatch(d -> d.getName().equals("Device3")));
    }

    @Test
    @Order(4)
    void testUpdateDeviceById() throws DeviceNotFoundException {
        Device device = new Device();
        device.setName("Device4");
        device.setSerialNumber("OrigSN");
        device.setModel("OrigModel");
        device.setManufacturer("OldMaker");
        device.setUser(savedUser);
        device = deviceRepository.save(device);

        DeviceDTO dto = new DeviceDTO();
        dto.setName("UpdatedDevice");
        dto.setSerialNumber("UpdatedSN");
        dto.setModel("UpdatedModel");
        dto.setManufacturer("NewMaker");

        Device updated = deviceService.updateById(device.getId(), dto);

        assertEquals("UpdatedDevice", updated.getName());
        assertEquals("UpdatedSN", updated.getSerialNumber());
    }

    @Test
    @Order(5)
    void testDeleteDeviceById() throws DeviceNotFoundException {
        Device device = new Device();
        device.setName("Device5");
        device.setSerialNumber("DEL123");
        device.setModel("ToDelete");
        device.setManufacturer("GoneMaker");
        device.setUser(savedUser);
        device = deviceRepository.save(device);

        deviceService.deleteById(device.getId());

        assertFalse(deviceRepository.findById(device.getId()).isPresent());
    }

    @Test
    @Order(6)
    void testCreateDevice_UserNotFound_Throws() {
        DeviceDTO dto = new DeviceDTO();
        dto.setName("InvalidUserDevice");
        dto.setSerialNumber("FAIL");
        dto.setModel("None");
        dto.setManufacturer("None");

        assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.create(9999L, dto);
        });
    }
}
