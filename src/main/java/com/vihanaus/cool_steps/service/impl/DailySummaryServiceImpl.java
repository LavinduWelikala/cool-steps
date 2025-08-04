package com.vihanaus.cool_steps.service.impl;

import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.repository.DailySummaryRepository;
import com.vihanaus.cool_steps.service.DailySummaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DailySummaryServiceImpl implements DailySummaryService {

    private DailySummaryRepository dailySummaryRepository;

    @Override
    public List<DailySummary> findAllByUser(Long userId) {

        return dailySummaryRepository.findAllByUser(userId);
    }
}
