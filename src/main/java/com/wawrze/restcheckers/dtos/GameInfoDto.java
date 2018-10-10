package com.wawrze.restcheckers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameInfoDto {

    private String name;
    private String rulesName;
    private String gameStatus;
    private String movesHistory;
    private BoardDto board;
    private boolean activePlayer;
    private boolean isWhiteAIPlayer;
    private boolean isBlackAIPlayer;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private String typeOfWin;
    private int moves;
    private int blackQueenMoves;
    private int whiteQueenMoves;
    private int whitePawns;
    private int blackPawns;
    private int whiteQueens;
    private int blackQueens;
    private DateTimeDto startTime;
    private DateTimeDto lastAction;

    public void setBoard(BoardDto board) {
        this.board = board;
    }

}