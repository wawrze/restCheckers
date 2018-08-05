package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.gameplay.RulesSet;

import java.util.List;

public class RestUI implements UserInterface {

    @Override
    public void printMoveHistory(List<String> moves) {

    }

    @Override
    public String getGameName() {
        return null;
    }

    @Override
    public String sideMenu(int line,List<String> moves, boolean player) {
        return null;
    }

    @Override
    public void printBoard(Board board, boolean simplePrint, boolean player, List<String> moves,
                    RulesSet rulesSet, boolean isItAITurn) {

    }

    @Override
    public void printMakingMove(boolean simplePrint, char x1, int y1, char x2, int y2, boolean isItAITurn) {

    }

    @Override
    public void printMoveDone(boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printCaptureDone(boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printIncorrectMove(String s, boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printCapture(String captures, boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printMultiCapture(String captures, boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printCaptureObligatory(boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public void printIncorrectMoveFormat(boolean simplePrint, boolean isItAITurn) {

    }

    @Override
    public String[] getMoveOrOption(String captures, boolean simplePrint, boolean isItAITurn) {
        return null;
    }

    @Override
    public boolean endOfGame(Board board, boolean simplePrint, List<String> moves, boolean player) {
        return false;
    }

}