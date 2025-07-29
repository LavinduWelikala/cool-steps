package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByUserAndDate(User user, LocalDate date);
}
