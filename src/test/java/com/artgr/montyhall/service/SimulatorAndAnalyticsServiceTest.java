package com.artgr.montyhall.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.data.presentation.GameResult;
import com.artgr.montyhall.data.presentation.SimulatorResult;
import com.artgr.montyhall.properties.SimulatorProperties;
import com.artgr.montyhall.utils.BoxNames;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SimulatorAndAnalyticsServiceTest {

    @Autowired
    private SimulatorAndAnalyticsService service;

    @MockBean
    private GameService gameService;

    @MockBean
    private SimulatorProperties simulatorProperties;

    @Test
    public void runSimulation_ok() {

        when(simulatorProperties.getIterations()).thenReturn(3);
        GameResult game = GameResult.builder()
                                    .gameId(ObjectId.get().toHexString())
                                    .selectedBox(BoxNames.BOX_1.getName())
                                    .winnerBox(BoxNames.BOX_1.getName())
                                    .emptyBox(BoxNames.BOX_2.getName())
                                    .isWinner(true)
                                    .build();

        when(gameService.quickGame(any(GameInput.class))).thenReturn(game);

        SimulatorResult result = service.runSimulation();
        Mockito.verify(gameService, times(3)).quickGame(any(GameInput.class));
        assertEquals(result.getNumberOfGames(), 3);
        assertNotNull(result.getBoxChangeToNonChangeWinsRatio());
        assertEquals(result.getNumberOfWins(), 3);
    }
}
