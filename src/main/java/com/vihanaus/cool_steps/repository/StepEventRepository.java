package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.Device;
import com.vihanaus.cool_steps.model.StepEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface StepEventRepository extends JpaRepository<StepEvent, Long> {

}
