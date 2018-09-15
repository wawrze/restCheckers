package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.*;
import com.wawrze.restcheckers.gameplay.userInterfaces.InGameUI;
import com.wawrze.restcheckers.gameplay.userInterfaces.UserInterface;
import com.wawrze.restcheckers.moves.*;
import exceptions.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Game implements Serializable {

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
    private boolean save;
    private LocalDate date;
    private LocalTime time;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    UserInterface inGameUI;

    public Game(Game game) {
        this.board = new Board(game.getBoard());
        this.moves = new LinkedList<>(game.getMoves());
        this.activePlayer = game.isActivePlayer();
        this.simplePrint = game.isSimplePrint();
        this.whiteQueenMoves = game.getWhiteQueenMoves();
        this.blackQueenMoves = game.getBlackQueenMoves();
        this.isFinished = game.isFinished();
        this.isDraw = game.isDraw();
        this.winner = game.isWinner();
        this.name = game.getName();
        this.save = game.isSave();
        this.date = game.getDate();
        this.time = game.getTime();
        this.rulesSet = game.getRulesSet();
        this.isBlackAIPlayer = game.isBlackAIPlayer();
        this.isWhiteAIPlayer = game.isWhiteAIPlayer();
        this.inGameUI = game.getInGameUI();
    }

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer) {
        board = new Board();
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
        save = false;
        this.rulesSet = rulesSet;

        board.setFigure('A', 2, new Pawn(true));
        board.setFigure('A', 4, new Pawn(true));
        board.setFigure('A', 6, new Pawn(true));
        board.setFigure('A', 8, new Pawn(true));
        board.setFigure('B', 1, new Pawn(true));
        board.setFigure('B', 3, new Pawn(true));
        board.setFigure('B', 5, new Pawn(true));
        board.setFigure('B', 7, new Pawn(true));
        board.setFigure('C', 2, new Pawn(true));
        board.setFigure('C', 4, new Pawn(true));
        board.setFigure('C', 6, new Pawn(true));
        board.setFigure('C', 8, new Pawn(true));

        board.setFigure('F', 1, new Pawn(false));
        board.setFigure('F', 3, new Pawn(false));
        board.setFigure('F', 5, new Pawn(false));
        board.setFigure('F', 7, new Pawn(false));
        board.setFigure('G', 2, new Pawn(false));
        board.setFigure('G', 4, new Pawn(false));
        board.setFigure('G', 6, new Pawn(false));
        board.setFigure('G', 8, new Pawn(false));
        board.setFigure('H', 1, new Pawn(false));
        board.setFigure('H', 3, new Pawn(false));
        board.setFigure('H', 5, new Pawn(false));
        board.setFigure('H', 7, new Pawn(false));
    }

    public boolean play(UserInterface inGameUI) throws IncorrectMoveFormat, IncorrectMoveException {
        this.inGameUI = inGameUI;
        boolean b;
        do {
            isFinished = VictoryValidator.validateEndOfGame(board, whiteQueenMoves, blackQueenMoves, activePlayer, rulesSet);
            if (isFinished) {
                save = inGameUI.endOfGame(board, simplePrint, moves, activePlayer);
                isDraw = VictoryValidator.isDraw();
                winner = VictoryValidator.getWinner();
                break;
            }
            b = this.waitForMove();
        } while (b);
        if(save && name.isEmpty()) {
            name = inGameUI.getGameName();
        }
        date = LocalDate.now();
        time = LocalTime.now();
        return save;
    }

    private boolean waitForMove() throws IncorrectMoveFormat, IncorrectMoveException {
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
        if(isBlackAIPlayer && activePlayer)
            s = (new AIPlayer1(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
        else if(isWhiteAIPlayer && !activePlayer)
            s = (new AIPlayer2(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves)).getAIMove();
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
            catch(IncorrectMoveFormat e){
                inGameUI.printIncorrectMoveFormat(simplePrint, isItAITurn);
                return true;
            }
        }
    }

    private void makeMove(String[] s) throws IncorrectMoveFormat, IncorrectMoveException {
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
            if(board.getFigure(move.getRow2(),move.getCol2()) instanceof Queen){
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
            multiCapture(move);
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
            if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'H')
                board.setFigure('H', move.getCol2(), new Queen(true));
            if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                    && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'A')
                board.setFigure('A', move.getCol2(), new Queen(false));
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
                if(isBlackAIPlayer && activePlayer) {
                    s = (new AIPlayer1(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves,
                            move.getRow2(), move.getCol2())).getAIMove();
                }
                else if(isWhiteAIPlayer && !activePlayer) {
                    s = (new AIPlayer2(board, activePlayer, rulesSet, whiteQueenMoves, blackQueenMoves,
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
        if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            board.setFigure('H', move.getCol2(), new Queen(true));
        if((board.getFigure(move.getRow2(), move.getCol2()) instanceof Pawn)
                && !board.getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            board.setFigure('A', move.getCol2(), new Queen(false));
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
                save = true;
                return true;
            default:
                save = false;
                return true;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        if(date == null || time == null)
            return "";
        String s = "";
        s += (date.getDayOfMonth() < 10 ? ("0" + date.getDayOfMonth()) : date.getDayOfMonth());
        s += ("." + (date.getMonthValue() < 10 ? ("0" + date.getMonthValue()) : date.getMonthValue()));
        s += ("." + date.getYear());
        s += (" " + (time.getHour() < 10 ? ("0" + time.getHour()) : time.getHour()));
        s += (":" + (time.getMinute() < 10 ? ("0" + time.getMinute()) : time.getMinute()));
        return name + " (\"" + rulesSet.getName() + "\" rules, " + moves.size() + " moves done, "
                + (isBlackAIPlayer ? "black: computer opponent, " : "black: human opponent, ")
                + (isWhiteAIPlayer ? "white: computer opponent, " : "white: human opponent, ")
                + (isFinished ? ("finished, " + (isDraw ? "draw)" : ("winner: "
                + (winner ? "black)" : "white)")))) : ("not finished)")) + ", date and time of save: " + s;
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

    private boolean isSimplePrint() {
        return simplePrint;
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

    private boolean isSave() {
        return save;
    }

    private LocalDate getDate() {
        return date;
    }

    private LocalTime getTime() {
        return time;
    }

    private RulesSet getRulesSet() {
        return rulesSet;
    }

    public boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    public boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

    private UserInterface getInGameUI() {return inGameUI;}

}