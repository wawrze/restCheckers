package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.gameplay.RulesSet;
import exceptions.IncorrectMoveFormat;

import java.util.*;

public class RestUI implements UserInterface {

    private Deque<String> inQueue = new ArrayDeque<>();
    private String gameStatus = "Game started.";

    public Deque<String> getInQueue() {
        return inQueue;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    @Override
    public String[] getMoveOrOption(String captures, boolean simplePrint, boolean isItAITurn) {
        String[] options = {"next", "x"};
        String s;
        if(inQueue.isEmpty())
            return null;
        s = inQueue.poll();
        s = s.toLowerCase();
        String[] result;
        for(String o : options) {
            if (s.equals(o)){
                result = new String[1];
                result[0] = s;
                return result;
            }
        }
        s = s.toUpperCase();
        try {
            validate(s);
            if(captures.isEmpty() || captures.contains(s)) {
                String[] sArray = s.split("-");
                char x1 = sArray[0].charAt(0);
                int y1 = Character.getNumericValue(sArray[0].charAt(1));
                char x2 = sArray[1].charAt(0);
                int y2 = Character.getNumericValue(sArray[1].charAt(1));
                result = new String[4];
                result[0] = "" + x1;
                result[1] = "" + y1;
                result[2] = "" + x2;
                result[3] = "" + y2;
            }
            else {
                printCaptureObligatory(simplePrint, isItAITurn);
                return null;
            }
        }
        catch(IncorrectMoveFormat e){
            printIncorrectMoveFormat(simplePrint, isItAITurn);
            return null;
        }
        return result;
    }

    @Override
    public void printMoveHistory(List<String> moves) {}

    @Override
    public String getGameName() {return null;}

    @Override
    public String sideMenu(int line,List<String> moves, boolean player) {return null;}

    @Override
    public void printBoard(Board board, boolean simplePrint, boolean player, List<String> moves,
                    RulesSet rulesSet, boolean isItAITurn) {}

    @Override
    public void printMakingMove(boolean simplePrint, char x1, int y1, char x2, int y2, boolean isItAITurn) {
        gameStatus = " Making move: " + x1 + y1 + "-" + x2 + y2;
    }

    @Override
    public void printMoveDone(boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Your opponent has made his move.";
    }

    @Override
    public void printCaptureDone(boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Your opponent has captured.";
    }

    @Override
    public void printIncorrectMove(String s, boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Incorrect move: " + s;
    }

    @Override
    public void printCapture(String captures, boolean simplePrint, boolean isItAITurn) {
        gameStatus = "You have to capture: " + captures;
    }

    @Override
    public void printMultiCapture(String captures, boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Possible captures: " + captures;
    }

    @Override
    public void printCaptureObligatory(boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Capture is obligatory!";
    }

    @Override
    public void printIncorrectMoveFormat(boolean simplePrint, boolean isItAITurn) {
        gameStatus = "Incorrect move format! Proper format example: E4-D5";
    }

    @Override
    public boolean endOfGame(Board board, boolean simplePrint, List<String> moves, boolean player) {
        gameStatus = "Game finished.";
        return false;
    }

    @Override
    public void waitForEnter() {
    }

    public void gameAlreadyExist(String gameName) {
        gameStatus = "Game \"" + gameName + "\" already exist. Game resumed.";
    }

    private void validate(String s) throws IncorrectMoveFormat {
        String[] sArray = s.split("-");
        if (sArray.length != 2)
            throw new IncorrectMoveFormat();
        for (String t : sArray)
            if (t.length() != 2)
                throw new IncorrectMoveFormat();
    }

}