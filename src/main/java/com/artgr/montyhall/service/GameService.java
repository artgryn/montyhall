package com.artgr.montyhall.service;

import com.artgr.montyhall.data.entity.Game;
import com.artgr.montyhall.data.presentation.GameResult;
import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.repository.GameRepository;
import com.artgr.montyhall.utils.BoxNames;
import com.artgr.montyhall.utils.Utils;
import com.artgr.montyhall.utils.Validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    @NonNull
    private GameRepository gameRepository;
    @NonNull
    private Validator validator;

    public GameResult quickGame(final GameInput gameInput) {

        val initialBoxSelection = gameInput.getSelectedBox();
        validator.validateBox(initialBoxSelection);

        // create game
        val game = Game.builder()
                       .winningBox(Utils.getRandomBox(BoxNames.ALL_BOXES))
                       .finalBoxSelection(initialBoxSelection) //may be changed
                       .initialBoxSelection(initialBoxSelection)
                       .build();

        // find empty box to show
        val boxesInGame = new HashSet<>(List.of(initialBoxSelection, game.getWinningBox()));
        val emptyBoxesToShow = Utils.getNotUsedBoxes(boxesInGame);
        val boxToShow = Utils.getRandomBox(emptyBoxesToShow);
        game.setEmptyBox(boxToShow);

        // if user wants to change initial selection
        if (gameInput.isChangeSelection()) {
            val usedBoxes = List.of(game.getEmptyBox(), initialBoxSelection);
            val changedBox = Utils.getNotUsedBoxes(usedBoxes);
            game.setFinalBoxSelection(changedBox.get(0)); // here only 1 element in the List
        }

        game.setIsWinner(game.getFinalBoxSelection().equals(game.getWinningBox()));
        val gameEntity = gameRepository.save(game);

        return GameResult.builder()
                         .gameId(gameEntity.getId().toHexString())
                         .emptyBox(game.getEmptyBox())
                         .selectedBox(game.getFinalBoxSelection())
                         .isChanged(gameInput.isChangeSelection())
                         .winnerBox(game.getWinningBox())
                         .isWinner(gameEntity.getIsWinner())
                         .build();
    }
}
