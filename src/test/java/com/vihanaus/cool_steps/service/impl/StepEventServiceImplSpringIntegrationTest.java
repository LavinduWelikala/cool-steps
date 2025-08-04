package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.*;
import com.vihanaus.cool_steps.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StepEventServiceImplSpringIntegrationTest {

    @Autowired
    private StepEventServiceImpl stepEventService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private StepEventRepository stepEventRepository;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    private User user;
    private Device device;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUserId("user123");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setCreatedAt(LocalDate.now());
        user = userRepository.save(user);

        device = new Device();
        device.setName("FitBand X");
        device.setSerialNumber("SN123456");
        device.setModel("FBX100");
        device.setManufacturer("CoolTech");
        device.setRegisteredAt(LocalDate.now());
        device.setUser(user);
        device = deviceRepository.save(device);
    }

    @Test
    void testCreateStepEvent_createsNewEventAndDailySummary() throws DeviceNotFoundException {
        StepEventDTO dto = new StepEventDTO();
        dto.setStepCount(100f);

        StepEvent result = stepEventService.create(device.getId(), dto);

        assertNotNull(result);
        assertEquals(device.getId(), result.getDevice().getId());
        assertEquals(100f, result.getStepCount());

        List<StepEvent> events = stepEventRepository.findAll();
        assertEquals(1, events.size());

        DailySummary summary = dailySummaryRepository.findByUserAndDate(user, LocalDate.now()).orElse(null);
        assertNotNull(summary);
        assertEquals(100f, summary.getSteps());
    }

    @Test
    void testCreateStepEvent_addsToExistingDailySummary() throws DeviceNotFoundException {
        DailySummary summary = new DailySummary();
        summary.setUser(user);
        summary.setDate(LocalDate.now());
        summary.setSteps(200f);
        dailySummaryRepository.save(summary);

        StepEventDTO dto = new StepEventDTO();
        dto.setStepCount(50f);

        StepEvent result = stepEventService.create(device.getId(), dto);

        assertNotNull(result);
        assertEquals(50f, result.getStepCount());

        DailySummary updated = dailySummaryRepository.findByUserAndDate(user, LocalDate.now()).orElse(null);
        assertNotNull(updated);
        assertEquals(250f, updated.getSteps());
    }

    @Test
    void testCreateStepEvent_deviceNotFound() {
        StepEventDTO dto = new StepEventDTO();
        dto.setStepCount(100f);

        assertThrows(DeviceNotFoundException.class, () -> {
            stepEventService.create(999L, dto);
        });
    }

    @Test
    void testFindAll_returnsAllStepEvents() throws DeviceNotFoundException {
        StepEventDTO dto = new StepEventDTO();
        dto.setStepCount(150f);
        stepEventService.create(device.getId(), dto);

        List<StepEvent> allEvents = stepEventService.findAll();
        assertEquals(1, allEvents.size());
    }

    @Test
    void testFindByAllByDeviceId_returnsFilteredEvents() throws DeviceNotFoundException {
        StepEventDTO dto1 = new StepEventDTO();
        dto1.setStepCount(60f);

        stepEventService.create(device.getId(), dto1);

        List<StepEvent> byDevice = stepEventService.findByAllIByDeviceId(device.getId());
        assertEquals(1, byDevice.size());
        assertEquals(60f, byDevice.get(0).getStepCount());
    }
}
