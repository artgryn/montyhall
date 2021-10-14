package com.artgr.montyhall.controller;

import com.artgr.montyhall.data.presentation.Analytics;
import com.artgr.montyhall.service.SimulatorAndAnalyticsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    @NonNull
    private SimulatorAndAnalyticsService analyticsService;

    @GetMapping
    public Analytics getAnalytics() {
        return analyticsService.getAnalytics();
    }
}
