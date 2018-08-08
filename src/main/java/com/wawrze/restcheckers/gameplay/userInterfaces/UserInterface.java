package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.gameplay.RulesSet;

import java.io.Serializable;
import java.util.List;

public interface UserInterface extends Serializable {

    void printMoveHistory(List<String> moves);

    String getGameName();

    String sideMenu(int line,List<String> moves, boolean player);

    void printBoard(Board board, boolean simplePrint, boolean player, List<String> moves,
                    RulesSet rulesSet, boolean isItAITurn);

    void printMakingMove(boolean simplePrint, char x1, int y1, char x2, int y2, boolean isItAITurn);

    void printMoveDone(boolean simplePrint, boolean isItAITurn);

    void printCaptureDone(boolean simplePrint, boolean isItAITurn);

    void printIncorrectMove(String s, boolean simplePrint, boolean isItAITurn);

    void printCapture(String captures, boolean simplePrint, boolean isItAITurn);

    void printMultiCapture(String captures, boolean simplePrint, boolean isItAITurn);

    void printCaptureObligatory(boolean simplePrint, boolean isItAITurn);

    void printIncorrectMoveFormat(boolean simplePrint, boolean isItAITurn);

    String[] getMoveOrOption(String captures, boolean simplePrint, boolean isItAITurn);

    boolean endOfGame(Board board, boolean simplePrint, List<String> moves, boolean player);

    void waitForEnter();

}