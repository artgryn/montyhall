package com.artgr.montyhall.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.artgr.montyhall.data.entity.Game;
import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.data.presentation.GameResult;
import com.artgr.montyhall.repository.GameRepository;
import com.artgr.montyhall.utils.BoxNames;
import com.artgr.montyhall.utils.Validator;

import org.bson.types.ObjectId;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private Validator validator;

    @Test
    public void testRunGame_ok() {

        GameInput input = GameInput.builder().selectedBox(BoxNames.BOX_1.getName()).isChangeSelection(false).build();

        Game gameEntity = Game.builder()
                              .id(ObjectId.get())
                              .isWinner(true)
                              .build();

        when(gameRepository.save(any(Game.class))).thenReturn(gameEntity);
        ArgumentCaptor<Game> valueCapture = ArgumentCaptor.forClass(Game.class);

        GameResult game = gameService.quickGame(input);
        Mockito.verify(gameRepository).save(valueCapture.capture());

        assertNotNull(game);
        assertNotNull(valueCapture);
        assertEquals(game.getGameId(), gameEntity.getId().toHexString());
        assertEquals(valueCapture.getValue().getInitialBoxSelection(), input.getSelectedBox());

        if (input.isChangeSelection()) {
            assertNotEquals(input.getSelectedBox(), valueCapture.getValue().getFinalBoxSelection());
        } else {
            assertEquals(input.getSelectedBox(), valueCapture.getValue().getFinalBoxSelection());
        }
    }
    @Test
    public void testRunGame_fail() {

        GameInput input = GameInput.builder().selectedBox("WRONG_BOX_NAME").isChangeSelection(false).build();
        doThrow(ResponseStatusException.class).when(validator).validateBox(any());

        try {
            gameService.quickGame(input);
            Assert.fail();
        } catch (ResponseStatusException e) {
            // Expected
        }
    }
}
