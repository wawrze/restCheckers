package com.wawrze.restcheckers.gameplay.userInterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameProgressDetailsDto {

    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private int moves;
    private int blackQueenMoves;
    private int whiteQueenMoves;
    private int whitePawns;
    private int blackPawns;
    private int whiteQueens;
    private int blackQueens;

}