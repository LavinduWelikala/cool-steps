package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.StepEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepEventRepository extends JpaRepository<StepEvent, Long> {
}
