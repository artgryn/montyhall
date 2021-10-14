package com.artgr.montyhall.data.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SimulatorResult {

    private int numberOfGames;
    private int numberOfWins;
    private int numberOfDecisionChange;
    private int winsAfterDecisionChange;
    private int winsWithoutDecisionChange;
    private double boxChangeToNonChangeWinsRatio;
}
