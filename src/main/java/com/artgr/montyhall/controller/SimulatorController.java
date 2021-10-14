package com.artgr.montyhall.controller;

import com.artgr.montyhall.data.presentation.SimulatorResult;
import com.artgr.montyhall.service.SimulatorAndAnalyticsService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulator")
@RequiredArgsConstructor
public class SimulatorController {

    @NonNull
    private SimulatorAndAnalyticsService simulatorService;

    /**
     * Run simulator to trigger game some number of time that defined in YML config file.
     * System will play this game randomly changing winning box, user box selection, opening an empty box and decision to change box or keep selected.
     *
     * @return stats about of the simulation round. Most important 'boxChangeToNonChangeWinsRatio' attribute.
     * boxChangeToNonChangeWinsRatio - indicates relation between user wins with and without box change.
     */
    @GetMapping
    private SimulatorResult runSimulator() {
        return simulatorService.runSimulation();
    }

}
