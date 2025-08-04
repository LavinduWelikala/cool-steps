package com.vihanaus.cool_steps.service;

import com.vihanaus.cool_steps.model.DailySummary;

import java.util.List;

public interface DailySummaryService {

    List<DailySummary> findAllByUser(Long userId);
}
