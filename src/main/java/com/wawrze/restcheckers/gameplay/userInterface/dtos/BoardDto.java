package com.wawrze.restcheckers.gameplay.userInterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardDto {

    private List<RowDto> rows;
    private String gameStatus;
    private boolean activePlayer;
    private boolean isWhiteAIPlayer;
    private boolean isBlackAIPlayer;
    private String movesHistory;

}