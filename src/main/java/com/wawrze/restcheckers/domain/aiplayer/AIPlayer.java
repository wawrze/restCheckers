package com.wawrze.restcheckers.domain.aiplayer;

import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class AIPlayer {

    private Board board;
    private boolean player;
    private boolean activePlayer;
    private RulesSet rulesSet;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private int depth;
    private Map<Move, Integer> possibleMoves;

    public void increaseWhiteQueenMoves() {
        this.whiteQueenMoves++;
    }

    public void increaseBlackQueenMoves() {
        this.blackQueenMoves++;
    }

}