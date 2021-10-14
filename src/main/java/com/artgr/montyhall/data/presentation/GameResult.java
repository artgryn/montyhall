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
public class GameResult {

    private String gameId;
    private String selectedBox;
    private String emptyBox;
    private boolean isChanged;
    private String winnerBox;
    private boolean isWinner;
}
