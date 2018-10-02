package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Game {

    private Board board;
    private List<String> moves;
    private Deque<String> inQueue;
    private String gameStatus;

    private boolean activePlayer;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private String name;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    private LocalDateTime startTime;

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer) {
        moves = new LinkedList<>();
        activePlayer = false;
        whiteQueenMoves = 0;
        blackQueenMoves = 0;
        isFinished = false;
        isDraw = false;
        this.isBlackAIPlayer = isBlackAIPlayer;
        this.isWhiteAIPlayer = isWhiteAIPlayer;
        this.name = name;
        this.rulesSet = rulesSet;
        this.startTime = LocalDateTime.now();
        gameStatus = "Game started.";
        inQueue  = new ArrayDeque<>();
        board = new Board.BoardBuilder().build().getNewBoard();
    }

}