package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class AIPlayer {

    private Board board;
    private boolean AIPlayer;
    private boolean activePlayer;
    private RulesSet rulesSet;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private int depth;
    private Map<Move,Integer> possibleMoves;

    public void setWhiteQueenMoves(int whiteQueenMoves) {
        this.whiteQueenMoves = whiteQueenMoves;
    }

    public void setBlackQueenMoves(int blackQueenMoves) {
        this.blackQueenMoves = blackQueenMoves;
    }

}