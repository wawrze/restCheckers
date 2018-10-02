package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.Game;
import exceptions.IncorrectMoveFormat;
import org.springframework.stereotype.Service;

@Service
public class RestUI  {

    public String[] getMoveOrOption(Game game, String captures) {
        String[] options = {"next", "x"};
        String s;
        if(game.getInQueue().isEmpty())
            return null;
        s = game.getInQueue().poll();
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
                printCaptureObligatory(game);
                return null;
            }
        }
        catch(IncorrectMoveFormat e) {
            printIncorrectMoveFormat(game);
            return null;
        }
        return result;
    }

    public void printMoveDone(Game game) {
        game.setGameStatus("Your opponent has made his move.");
    }

    public void printCaptureDone(Game game) {
        game.setGameStatus("Your opponent has captured.");
    }

    public void printIncorrectMove(Game game, String s) {
        game.setGameStatus("Incorrect move: " + s);
    }

    public void printCapture(Game game, String captures) {
        game.setGameStatus("You have to capture: " + captures);
    }

    public void printMultiCapture(Game game, String captures) {
        game.setGameStatus("Possible captures: " + captures);
    }

    public void printCaptureObligatory(Game game) {
        game.setGameStatus("Capture is obligatory!");
    }

    public void printIncorrectMoveFormat(Game game) {
        game.setGameStatus("Incorrect move format! Proper format example: E4-D5");
    }

    public void gameAlreadyExist(Game game, String gameName) {
        game.setGameStatus("Game \"" + gameName + "\" already exist. Game resumed.");
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