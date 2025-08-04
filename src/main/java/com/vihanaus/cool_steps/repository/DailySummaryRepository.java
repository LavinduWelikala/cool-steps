package com.vihanaus.cool_steps.repository;

import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.model.StepEvent;
import com.vihanaus.cool_steps.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByUserAndDate(User user, LocalDate date);

    @Query("SELECT d FROM DailySummary d WHERE d.user.id = :userId")
    List<DailySummary> findAllByUser(@Param("userId") Long userId);
}
