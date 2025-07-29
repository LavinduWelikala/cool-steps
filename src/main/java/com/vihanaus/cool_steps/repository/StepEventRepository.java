package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.StepEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StepEventRepository extends JpaRepository<StepEvent, Long> {

    @Query("SELECT s FROM StepEvent s WHERE s.device.id = :deviceId")
    List<StepEvent> findAllByDeviceId(@Param("deviceId") Long deviceId);
}
