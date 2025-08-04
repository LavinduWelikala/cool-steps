package com.vihanaus.cool_steps.controller;

import com.vihanaus.cool_steps.controller.response.DailySummaryResponse;
import com.vihanaus.cool_steps.model.DailySummary;
import com.vihanaus.cool_steps.service.DailySummaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
public class DailySummaryController {

    private DailySummaryService dailySummaryService;

    @GetMapping("/users/{user-id}/daily-summaries")
    public List<DailySummaryResponse> findAll(@PathVariable("user-id") Long userId) {

        List<DailySummary> dailySummaryList = dailySummaryService.findAllByUser(userId);

        List<DailySummaryResponse> dailySummaryResponses = new ArrayList<>();

        for (DailySummary dailySummary : dailySummaryList) {

            DailySummaryResponse dailySummaryResponse = new DailySummaryResponse();
            dailySummaryResponse.setDailySummaryId(dailySummary.getId());
            dailySummaryResponse.setDate(dailySummary.getDate());
            dailySummaryResponse.setSteps(dailySummary.getSteps());
            dailySummaryResponse.setUserId(dailySummary.getUser().getId());
            dailySummaryResponses.add(dailySummaryResponse);
        }
        
        return dailySummaryResponses;
    }
}
