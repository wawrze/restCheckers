package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.FigureFactory;
import com.wawrze.restcheckers.gameplay.userInterface.RestUI;
import com.wawrze.restcheckers.moves.*;
import exceptions.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Game {

    private FigureFactory figureFactory = new FigureFactory();

    private Board board;
    private List<String> moves;

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
    private RestUI inGameUI;
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

        board = new Board.BoardBuilder().build().getNewBoard();
    }

    public void play(RestUI inGameUI) {
        this.inGameUI = inGameUI;
        boolean b;
        do {
            isFinished = VictoryValidator.validateEndOfGame(
                    board,
                    whiteQueenMoves,
                    blackQueenMoves,
                    activePlayer,
                    rulesSet
            );
            if (isFinished) {
                isDraw = VictoryValidator.isDraw();
                winner = VictoryValidator.getWinner();
                break;
            }
            b = this.waitForMove();
        } while (b);
    }

    private boolean waitForMove() {
        String captures = "";
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
        }
        catch(CapturePossibleException e){
            captures = e.getMessage();
            inGameUI.printCapture(captures);
        }
        String[] s;
        if((isBlackAIPlayer && activePlayer) || (isWhiteAIPlayer && !activePlayer)) {
            s = (new AIPlayer(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
            if(isBlackAIPlayer && isWhiteAIPlayer) {
                String[] t;
                do {
                    t = inGameUI.getMoveOrOption(captures);
                }
                while (t == null);
            }
        }
        else {
            s = inGameUI.getMoveOrOption(captures);
        }
        if(s == null)
            return true;
        else if(s.length == 1) {
            if (s[0].equals("x"))
                return false;
            else
                return true;
        }
        else {
            try {
                this.makeMove(s);
                return true;
            }
            catch(IncorrectMoveFormat e) {
                inGameUI.printIncorrectMoveFormat();
                return true;
            }
        }
    }

    private void makeMove(String[] s) throws IncorrectMoveFormat {
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        Move move = new Move(x1, y1, x2, y2);
        try {
            MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeMove(board);
            if(board.getFigure(move.getRow2(),move.getCol2()).getFigureName().equals(Figure.QUEEN)){
                if(activePlayer)
                    blackQueenMoves++;
                else
                    whiteQueenMoves++;
            }
            else{
                if(activePlayer)
                    blackQueenMoves = 0;
                else
                    whiteQueenMoves = 0;
            }
            this.activePlayer = !this.activePlayer;
            inGameUI.printMoveDone();

        }
        catch (CaptureException e) {
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeCapture(board,e.getRow(),e.getCol());
            try {
                multiCapture(move);
            }
            catch(IncorrectMoveException e1) {}
            inGameUI.printCaptureDone();
            if(activePlayer)
                blackQueenMoves = 0;
            else
                whiteQueenMoves = 0;
            this.activePlayer = !this.activePlayer;
        }
        catch (IncorrectMoveException e) {
            inGameUI.printIncorrectMove(e.getMessage());
        }
        finally {
            if((board.getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                    && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'H')
                board.setFigure('H', move.getCol2(), figureFactory.getNewFigure(true, Figure.QUEEN));
            if((board.getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                    && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'A')
                board.setFigure('A', move.getCol2(), figureFactory.getNewFigure(false, Figure.QUEEN));
        }
    }

    private void multiCapture(Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        do {
            try{
                (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                        .validateCapturePossibilityForOneFigure(move.getRow2(),move.getCol2());
                break;
            }
            catch(CapturePossibleException e){
                inGameUI.printMultiCapture(e.getMessage());
                String[] s;
                if((isBlackAIPlayer && activePlayer) || (isWhiteAIPlayer && !activePlayer)) {
                    s = (new AIPlayer(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves,
                            move.getRow2(), move.getCol2())).getAIMove();
                }
                else {
                    s = inGameUI.getMoveOrOption(e.getMessage());
                }
                if(s == null)
                    continue;
                else {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    move = new Move(x1, y1, x2, y2);
                    try{
                        MoveValidator.validateMove(move, this.board, this.activePlayer, rulesSet);
                    }
                    catch(CaptureException e1){
                        moves.add((activePlayer ? "black: " : "white: ") + move);
                        move.makeCapture(board,e1.getRow(),e1.getCol());
                    }
                    finally {}
                }
                continue;
            }
        } while(true);
        if((board.getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            board.setFigure('H', move.getCol2(), figureFactory.getNewFigure(true, Figure.QUEEN));
        if((board.getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            board.setFigure('A', move.getCol2(), figureFactory.getNewFigure(false, Figure.QUEEN));
    }

}