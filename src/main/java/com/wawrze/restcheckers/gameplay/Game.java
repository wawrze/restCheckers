package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.FigureFactory;
import com.wawrze.restcheckers.gameplay.userInterface.UserInterface;
import com.wawrze.restcheckers.moves.*;
import exceptions.*;

import java.util.LinkedList;
import java.util.List;

public class Game {

    private FigureFactory figureFactory = new FigureFactory();

    private Board board;
    private List<String> moves;

    private boolean activePlayer;
    private boolean simplePrint;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private String name;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    UserInterface inGameUI;

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer) {

        moves = new LinkedList<>();
        activePlayer = false;
        simplePrint = false;
        whiteQueenMoves = 0;
        blackQueenMoves = 0;
        isFinished = false;
        isDraw = false;
        this.isBlackAIPlayer = isBlackAIPlayer;
        this.isWhiteAIPlayer = isWhiteAIPlayer;
        this.name = name;
        this.rulesSet = rulesSet;

        board = new Board.BoardBuilder().build().getNewBoard();
    }

    public void play(UserInterface inGameUI) {
        this.inGameUI = inGameUI;
        boolean b;
        do {
            isFinished = VictoryValidator.validateEndOfGame(board, whiteQueenMoves, blackQueenMoves, activePlayer, rulesSet);
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
        boolean isItAITurn = false;
        if(isBlackAIPlayer && activePlayer)
            isItAITurn = true;
        if(isWhiteAIPlayer && !activePlayer)
            isItAITurn = true;
        inGameUI.printBoard(board, simplePrint, activePlayer, moves, rulesSet, isItAITurn);
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
        }
        catch(CapturePossibleException e){
            captures = e.getMessage();
            inGameUI.printCapture(captures, simplePrint, isItAITurn);
        }
        String[] s;
        if((isBlackAIPlayer && activePlayer) || (isWhiteAIPlayer && !activePlayer)) {
            s = (new AIPlayer(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
            if(isBlackAIPlayer && isWhiteAIPlayer) {
                String[] t;
                do {
                    t = inGameUI.getMoveOrOption(captures, simplePrint, isItAITurn);
                }
                while (t == null);
            }
        }
        else {
            s = inGameUI.getMoveOrOption(captures, simplePrint, isItAITurn);
        }
        if(s == null)
            return true;
        else if(s.length == 1 && inGameMenu(s[0])){
            if (s[0].equals("x") || s[0].equals("s"))
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
                inGameUI.printIncorrectMoveFormat(simplePrint, isItAITurn);
                return true;
            }
        }
    }

    private void makeMove(String[] s) throws IncorrectMoveFormat {
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        boolean isItAITurn = false;
        if(isBlackAIPlayer && activePlayer)
            isItAITurn = true;
        if(isWhiteAIPlayer && !activePlayer)
            isItAITurn = true;
        inGameUI.printMakingMove(simplePrint, x1, y1, x2, y2, isItAITurn);
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
            inGameUI.printMoveDone(simplePrint, isItAITurn);

        }
        catch (CaptureException e) {
            moves.add((activePlayer ? "black: " : "white: ") + move);
            move.makeCapture(board,e.getRow(),e.getCol());
            try {
                multiCapture(move);
            }
            catch(IncorrectMoveException e1) {}
            inGameUI.printCaptureDone(simplePrint, isItAITurn);
            if(activePlayer)
                blackQueenMoves = 0;
            else
                whiteQueenMoves = 0;
            this.activePlayer = !this.activePlayer;
        }
        catch (IncorrectMoveException e) {
            inGameUI.printIncorrectMove(e.getMessage(), simplePrint, isItAITurn);
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
            if(!isItAITurn)
                inGameUI.waitForEnter(); }
    }

    private void multiCapture(Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        do {
            try{
                (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                        .validateCapturePossibilityForOneFigure(move.getRow2(),move.getCol2());
                break;
            }
            catch(CapturePossibleException e){
                boolean isItAITurn = false;
                if(isBlackAIPlayer && activePlayer)
                    isItAITurn = true;
                if(isWhiteAIPlayer && !activePlayer)
                    isItAITurn = true;
                inGameUI.printBoard(board, simplePrint, activePlayer, moves, rulesSet, isItAITurn);
                inGameUI.printMultiCapture(e.getMessage(), simplePrint, isItAITurn);
                String[] s;
                if((isBlackAIPlayer && activePlayer) || (isWhiteAIPlayer && !activePlayer)) {
                    s = (new AIPlayer(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves,
                            move.getRow2(), move.getCol2())).getAIMove();
                }
                else {
                    s = inGameUI.getMoveOrOption(e.getMessage(), simplePrint, isItAITurn);
                }
                if(s == null)
                    continue;
                else if(s.length == 1 && inGameMenu(s[0])){
                    continue;
                }
                else {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    inGameUI.printMakingMove(simplePrint, x1, y1, x2, y2, isItAITurn);
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

    private boolean inGameMenu(String s) {
        switch(s) {
            case "h":
                inGameUI.printMoveHistory(moves);
                inGameUI.waitForEnter();
                return true;
            case "p":
                this.simplePrint = !this.simplePrint;
                return true;
            case "s":
                return true;
            default:
                return true;
        }
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public List<String> getMoves() {
        return moves;
    }

    public boolean isActivePlayer() {
        return activePlayer;
    }

    public int getWhiteQueenMoves() {
        return whiteQueenMoves;
    }

    public int getBlackQueenMoves() {
        return blackQueenMoves;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public boolean isWinner() {
        return winner;
    }

    public RulesSet getRulesSet() {
        return rulesSet;
    }

    public boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    public boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

}