package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.controller.request.StepEventDTO;
import com.vihanaus.cool_steps.exception.DeviceNotFoundException;
import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.StepEvent;
import com.vihanaus.cool_steps.model.User;
import com.vihanaus.cool_steps.repository.DailySummaryRepository;
import com.vihanaus.cool_steps.repository.DeviceRepository;
import com.vihanaus.cool_steps.repository.StepEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StepEventServiceImplSpringUnitTest {

    @Mock
    private StepEventRepository stepEventRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DailySummaryRepository dailySummaryRepository;

    @InjectMocks
    private StepEventServiceImpl stepEventService;

    private Device mockDevice;
    private User mockUser;
    private DailySummary mockSummary;
    private StepEventDTO stepEventDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        mockDevice = new Device();
        mockDevice.setId(1L);
        mockDevice.setUser(mockUser);

        mockSummary = new DailySummary();
        mockSummary.setId(1L);
        mockSummary.setUser(mockUser);
        mockSummary.setDate(LocalDate.now());
        mockSummary.setSteps(100f);

        stepEventDTO = new StepEventDTO();
        stepEventDTO.setStepCount(50f);
    }

    @Test
    void testCreateStepEvent_Successful() throws DeviceNotFoundException {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(mockDevice));
        when(dailySummaryRepository.findByUserAndDate(any(), any())).thenReturn(Optional.of(mockSummary));
        when(stepEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(dailySummaryRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        StepEvent result = stepEventService.create(1L, stepEventDTO);

        assertNotNull(result);
        assertEquals(mockDevice, result.getDevice());
        assertEquals(stepEventDTO.getStepCount(), result.getStepCount());
        verify(stepEventRepository, times(1)).save(any(StepEvent.class));
        verify(dailySummaryRepository, times(1)).save(mockSummary);
    }

    @Test
    void testCreateStepEvent_CreatesNewSummary() throws DeviceNotFoundException {
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(mockDevice));
        when(dailySummaryRepository.findByUserAndDate(any(), any())).thenReturn(Optional.empty());
        when(dailySummaryRepository.save(any())).thenAnswer(i -> {
            DailySummary ds = i.getArgument(0);
            ds.setId(999L);
            return ds;
        });
        when(stepEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        StepEvent result = stepEventService.create(1L, stepEventDTO);

        assertNotNull(result);
        assertEquals(50f, result.getStepCount());
        verify(dailySummaryRepository, times(2)).save(any());
    }

    @Test
    void testCreateStepEvent_DeviceNotFound() {
        when(deviceRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> {
            stepEventService.create(1L, stepEventDTO);
        });

        verify(stepEventRepository, never()).save(any());
        verify(dailySummaryRepository, never()).save(any());
    }

    @Test
    void testFindAll_ReturnsStepEvents() {
        StepEvent event = new StepEvent();
        when(stepEventRepository.findAll()).thenReturn(List.of(event));

        List<StepEvent> events = stepEventService.findAll();

        assertEquals(1, events.size());
    }

    @Test
    void testFindAllByDeviceId_ReturnsCorrectList() {
        StepEvent event = new StepEvent();
        when(stepEventRepository.findAllByDeviceId(1L)).thenReturn(List.of(event));

        List<StepEvent> events = stepEventService.findByAllIByDeviceId(1L);

        assertEquals(1, events.size());
        verify(stepEventRepository).findAllByDeviceId(1L);
    }
}
