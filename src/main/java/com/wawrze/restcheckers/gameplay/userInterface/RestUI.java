package com.wawrze.restcheckers.gameplay.userInterface;

import exceptions.IncorrectMoveFormat;

import java.util.*;

public class RestUI  {

    private Deque<String> inQueue = new ArrayDeque<>();
    private String gameStatus = "Game started.";

    public Deque<String> getInQueue() {
        return inQueue;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public String[] getMoveOrOption(String captures) {
        String[] options = {"next", "x"};
        String s;
        if(inQueue.isEmpty())
            return null;
        /////////////////////////////////////
        inQueue.stream()
                .forEach(System.out::println);
        /////////////////////////////////////
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
                printCaptureObligatory();
                return null;
            }
        }
        catch(IncorrectMoveFormat e){
            printIncorrectMoveFormat();
            return null;
        }
        return result;
    }

    public void printMoveDone() {
        gameStatus = "Your opponent has made his move.";
    }

    public void printCaptureDone() {
        gameStatus = "Your opponent has captured.";
    }

    public void printIncorrectMove(String s) {
        gameStatus = "Incorrect move: " + s;
    }

    public void printCapture(String captures) {
        gameStatus = "You have to capture: " + captures;
    }

    public void printMultiCapture(String captures) {
        gameStatus = "Possible captures: " + captures;
    }

    public void printCaptureObligatory() {
        gameStatus = "Capture is obligatory!";
    }

    public void printIncorrectMoveFormat() {
        gameStatus = "Incorrect move format! Proper format example: E4-D5";
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