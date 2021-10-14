package com.artgr.montyhall.controller;

import com.artgr.montyhall.data.presentation.GameResult;
import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.service.GameService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    @NonNull
    private GameService gameService;

    /**
     * Run single game round.
     * 1. initiate game
     * 2. select box according to input
     * 3. opens one box which is empty
     * 4. keep selected box or change it according to the input
     * 5. return the result
     *
     * @param gameInput - {selectedBox, isChangeSelection} = initial box selection and flag that allows to change selected box after empty one exposed.
     * @return game result with all steps and winning box.
     */
    @PostMapping
    public GameResult quickGame(@RequestBody final GameInput gameInput) {

        return gameService.quickGame(gameInput);
    }

}
