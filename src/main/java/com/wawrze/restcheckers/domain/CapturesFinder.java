package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.domain.board.Board;

import java.util.ArrayList;
import java.util.List;

public class CapturesFinder {

    private List<String> listOfCaptures;
    private Board board;
    private boolean player;
    private RulesSet rulesSet;
    private int[] counter;
    private int maxDepth;

    public CapturesFinder(Board board, boolean player, RulesSet rulesSet) {
        listOfCaptures = new ArrayList<>();
        this.board = board;
        this.player = player;
        this.rulesSet = rulesSet;
    }

    public List<String> getListOfCaptures() {
        return listOfCaptures;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPlayer() {
        return player;
    }

    public RulesSet getRulesSet() {
        return rulesSet;
    }

    public int[] getCounter() {
        return counter;
    }

    public void setCounter(int[] counter) {
        this.counter = counter;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

}