package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.DeviceDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DeviceServiceImplSpringUnitTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private User testUser;
    private Device testDevice;
    private DeviceDTO testDeviceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");

        testDevice = new Device();
        testDevice.setId(10L);
        testDevice.setName("FitBand");
        testDevice.setSerialNumber("SN123");
        testDevice.setModel("FB-2022");
        testDevice.setManufacturer("FitCo");
        testDevice.setUser(testUser);

        testDeviceDTO = new DeviceDTO();
        testDeviceDTO.setName("FitBand");
        testDeviceDTO.setSerialNumber("SN123");
        testDeviceDTO.setModel("FB-2022");
        testDeviceDTO.setManufacturer("FitCo");
    }

    @Test
    void createDevice_Success() throws DeviceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(deviceRepository.save(any(Device.class))).thenReturn(testDevice);

        Device result = deviceService.create(1L, testDeviceDTO);

        assertNotNull(result);
        assertEquals("FitBand", result.getName());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void createDevice_UserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.create(999L, testDeviceDTO));
    }

    @Test
    void findAllDevices() {
        when(deviceRepository.findAll()).thenReturn(Arrays.asList(testDevice));

        List<Device> devices = deviceService.findAll();

        assertEquals(1, devices.size());
        assertEquals("FitBand", devices.get(0).getName());
    }

    @Test
    void findDeviceById_Success() throws DeviceNotFoundException {
        when(deviceRepository.findById(10L)).thenReturn(Optional.of(testDevice));

        Device result = deviceService.findById(10L);

        assertEquals("FitBand", result.getName());
    }

    @Test
    void findDeviceById_NotFound() {
        when(deviceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.findById(999L));
    }

    @Test
    void updateDeviceById_Success() throws DeviceNotFoundException {
        when(deviceRepository.findById(10L)).thenReturn(Optional.of(testDevice));
        when(deviceRepository.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Device updated = deviceService.updateById(10L, testDeviceDTO);

        assertNotNull(updated);
        assertEquals("FitBand", updated.getName());
        assertEquals("SN123", updated.getSerialNumber());
    }

    @Test
    void updateDeviceById_NotFound() {
        when(deviceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateById(999L, testDeviceDTO));
    }

    @Test
    void deleteDeviceById() {
        doNothing().when(deviceRepository).deleteById(10L);

        deviceService.deleteById(10L);

        verify(deviceRepository, times(1)).deleteById(10L);
    }
}
