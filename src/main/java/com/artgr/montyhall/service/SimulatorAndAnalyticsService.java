package com.artgr.montyhall.service;

import com.artgr.montyhall.data.presentation.Analytics;
import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.data.presentation.SimulatorResult;
import com.artgr.montyhall.properties.SimulatorProperties;
import com.artgr.montyhall.repository.GameRepository;
import com.artgr.montyhall.utils.BoxNames;
import com.artgr.montyhall.utils.Utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SimulatorAndAnalyticsService {

    @NonNull
    private GameService gameService;

    @NonNull
    private GameRepository gameRepository;

    @NonNull
    private SimulatorProperties simulatorProperties;

    public SimulatorResult runSimulation() {

        // uses arrays instead of int because want to iterate in lambda
        val winGames = new ArrayList<>();
        val gamesWithDecisionChange = new ArrayList<>();
        val winsAfterDecisionChange = new ArrayList<>();

        IntStream.range(0, simulatorProperties.getIterations()).forEach($ -> {
            val r = new Random();
            val game = gameService.quickGame(GameInput.builder()
                                                      .selectedBox(Utils.getRandomBox(BoxNames.ALL_BOXES))
                                                      .isChangeSelection(r.nextBoolean())
                                                      .build());

            if (game.isWinner()) {
                winGames.add(game.getGameId());
            }

            if (game.isChanged()) {
                gamesWithDecisionChange.add(game.getGameId());
            }

            if (game.getWinnerBox().equals(game.getSelectedBox()) && game.isChanged()) {
                winsAfterDecisionChange.add(game.getGameId());
            }

        });

        val winsWithoutDecisionChange = winGames.size() - winsAfterDecisionChange.size();
        val boxChangeToNonChangeWinsRatio = winsWithoutDecisionChange != 0 ?
                                            Double.valueOf(winsAfterDecisionChange.size()) / Double.valueOf(winsWithoutDecisionChange) : 0;

        return SimulatorResult.builder()
                              .numberOfGames(simulatorProperties.getIterations())
                              .numberOfWins(winGames.size())
                              .numberOfDecisionChange(gamesWithDecisionChange.size())
                              .winsAfterDecisionChange(winsAfterDecisionChange.size())
                              .winsWithoutDecisionChange(winsWithoutDecisionChange)
                              .boxChangeToNonChangeWinsRatio(boxChangeToNonChangeWinsRatio)
                              .build();
    }

    public Analytics getAnalytics() {

        val data = gameRepository.aggregateBasicData();
        if (data.getMappedResults() == null || data.getMappedResults().isEmpty()) {
            return null;
        }

        return Analytics.builder()
                        .mostWinningBox(data.getMappedResults().get(0).getId())
                        .totalNumberOfWins(data.getMappedResults().get(0).getCount())
                        .build();
    }

}
