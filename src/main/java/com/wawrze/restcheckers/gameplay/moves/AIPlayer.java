package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.gameplay.VictoryValidator;
import exceptions.*;

import java.util.*;
import java.util.stream.IntStream;

public class AIPlayer {

    private final int MAX_DEPTH = 3;

    private Board board;
    private boolean AIPlayer;
    private boolean activePlayer;
    private RulesSet rulesSet;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private int depth;
    private Map<Move,Integer> possibleMoves;

    public AIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves) {
        this.board = board;
        this.AIPlayer = player;
        this.activePlayer = player;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = 1;
        possibleMoves = new HashMap<>();
        getPossibleMoves();
        evaluateMoves();
    }

    public AIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves, char row,
                    int col) {
        this.board = board;
        this.AIPlayer = player;
        this.activePlayer = player;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = 1;
        possibleMoves = new HashMap<>();
        getPossibleMovesMultiCapture(row, col);
        evaluateMoves();
    }

    private AIPlayer(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                     int blackQueenMoves, int depth) {
        this.board = board;
        this.AIPlayer = AIPlayer;
        this.activePlayer = activePlayer;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = depth + 1;
        possibleMoves = new HashMap<>();
        getPossibleMoves();
        evaluateMoves();
    }

    private AIPlayer(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                     int blackQueenMoves, int depth, char row, int col) {
        this.board = board;
        this.AIPlayer = AIPlayer;
        this.activePlayer = activePlayer;
        this.rulesSet = rulesSet;
        this.whiteQueenMoves = whiteQueenMoves;
        this.blackQueenMoves = blackQueenMoves;
        this.depth = depth + 1;
        possibleMoves = new HashMap<>();
        getPossibleMovesMultiCapture(row, col);
        evaluateMoves();
    }

    private void evaluateMoves() {
        Map<Move,Integer> moves = new HashMap<>(possibleMoves);
        boolean capture;
        int value;
        for(Map.Entry<Move,Integer> entry : moves.entrySet()){
            Board tmpBoard = new Board(board);
            capture = false;
            try{
                MoveValidator.validateMove(entry.getKey(), tmpBoard, activePlayer, rulesSet);
                entry.getKey().makeMove(tmpBoard);
                if(tmpBoard.getFigure(
                        entry.getKey().getRow2(),
                        entry.getKey().getCol2()).getFigureName().equals(Figure.QUEEN)
                        ) {
                    if(activePlayer)
                        blackQueenMoves++;
                    else
                        whiteQueenMoves++;
                }
                Game tmpGame = new Game("tmpGame", rulesSet, true, true);
                tmpGame.setBoard(tmpBoard);
                tmpGame.setWhiteQueenMoves(whiteQueenMoves);
                tmpGame.setBlackQueenMoves(blackQueenMoves);
                tmpGame.setActivePlayer(activePlayer);
                    if(VictoryValidator.validateEndOfGame(tmpGame)) {
                        value = evaluateWhenEndOfGame();
                }
                else {
                    value = 1;
                }
            }
            catch(CaptureException e) {
                entry.getKey().makeCapture(tmpBoard, e.getRow(), e.getCol());
                try {
                    (new CapturePossibilityValidator(tmpBoard, activePlayer, rulesSet))
                            .validateCapturePossibilityForOneFigure(entry.getKey().getRow2(), entry.getKey().getCol2());
                    Game tmpGame = new Game("tmpGame", rulesSet, true, true);
                    tmpGame.setBoard(tmpBoard);
                    tmpGame.setWhiteQueenMoves(whiteQueenMoves);
                    tmpGame.setBlackQueenMoves(blackQueenMoves);
                    tmpGame.setActivePlayer(activePlayer);
                    if(VictoryValidator.validateEndOfGame(tmpGame)) {
                        value = evaluateWhenEndOfGame();
                    }
                    else {
                        value = 100;
                    }
                }
                catch(CapturePossibleException e1) {
                    value = 100;
                    capture = true;
                }
            }
            catch(IncorrectMoveException e) {
                value = 0;
            }
            if(activePlayer != AIPlayer)
                value *= -1;
            value += getFigureSetEvaluation(tmpBoard);
            if(depth < MAX_DEPTH){
                if(capture) {
                    AIPlayer next_move = new AIPlayer(tmpBoard, AIPlayer, activePlayer, rulesSet, whiteQueenMoves,
                            blackQueenMoves, depth, entry.getKey().getRow2(), entry.getKey().getCol2());
                    value += next_move.getMovesMapValue();
                }
                else {
                    AIPlayer next_move = new AIPlayer(tmpBoard, AIPlayer, !activePlayer, rulesSet, whiteQueenMoves,
                            blackQueenMoves, depth);
                    value += next_move.getMovesMapValue();
                }
            }
            possibleMoves.replace(entry.getKey(), value);
        }
    }

    private int getMovesMapValue(){
        return possibleMoves.entrySet().stream()
                .mapToInt(entry -> entry.getValue())
                .sum();
    }

    private int evaluateWhenEndOfGame(){
        if (VictoryValidator.isDraw())
            return 0;
        else if(VictoryValidator.getWinner() == activePlayer)
            return 10000;
        else
            return -10000;
    }

    private void getPossibleMovesMultiCapture(char row, int col) {
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet))
                    .validateCapturePossibilityForOneFigure(row, col);
        }
        catch(CapturePossibleException e) {
            moveListFromCaptures(e.getMessage());
        }
        finally {}
    }

    private void moveListFromCaptures(String captureList) {
        String[] sArray = captureList.split(" ");
        for(String s : sArray){
            String[] sA = s.split("-");
            char x1 = sA[0].charAt(0);
            int y1 = Character.getNumericValue(sA[0].charAt(1));
            char x2 = sA[1].charAt(0);
            int y2 = Character.getNumericValue(sA[1].charAt(1));
            try {
                Move move = new Move(x1, y1, x2, y2);
                possibleMoves.put(move, 1);
            }
            catch(IncorrectMoveFormat e1){}
        }
    }

    private void getPossibleMoves() {
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
            IntStream.iterate(1, i -> ++i)
                    .limit(8)
                    .forEach(i ->
                            IntStream.iterate(65, j -> ++j)
                                    .limit(8)
                                    .filter(j -> !(board.getFigure((char) j, i).getFigureName().equals(Figure.NONE)))
                                    .filter(j -> board.getFigure((char) j, i).getColor() == activePlayer)
                                    .forEach(j -> getFigureMovePossibility((char) j, i))
                    );
        }
        catch(CapturePossibleException e) {
            moveListFromCaptures(e.getMessage());
        }
    }

    private int getFigureSetEvaluation(Board board) {
        int value = 0;
        for(int i = 1;i<9;i++)
            for(int j = 65;j < 73;j++)
                if (!(board.getFigure((char) j, i).getFigureName().equals(Figure.NONE))) {
                    if (board.getFigure((char) j, i).getFigureName().equals(Figure.QUEEN))
                        value += board.getFigure((char) j, i).getColor() ? -80 : 80;
                    else
                        value += board.getFigure((char) j, i).getColor() ? -(j - 64) : -(j - 73);
                }
        if(activePlayer != AIPlayer)
            value *= -1;
        return value;
    }

    private void getFigureMovePossibility(char row, int col) {
        int range = 8;
        if(board.getFigure(row,col).getFigureName().equals(Figure.PAWN) || rulesSet.isQueenRangeOne())
            range = 3;
        char row2;
        int col2;
        for(int i = 1;i<range;i++) {
            //left-up
            row2 = (char) (((int) row) - i);
            col2 = col - i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //right-up
            row2 = (char) (((int) row) - i);
            col2 = col + i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //right-down
            row2 = (char) (((int) row) + i);
            col2 = col + i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
        for(int i = 1;i<range;i++) {
            //left-down
            row2 = (char) (((int) row) + i);
            col2 = col - i;
            if(!validateMove(row, col, row2, col2))
                break;
        }
    }

    private boolean validateMove(char row, int col, char row2, int col2){
        Move move;
        try {
            move = new Move(row, col, row2, col2);
        }
        catch (IncorrectMoveFormat e) {
            return false;
        }
        try {
            MoveValidator.validateMove(move, board, board.getFigure(row, col).getColor(), rulesSet);
            possibleMoves.put(move, 0);
        } catch (IncorrectMoveException | CaptureException e) {}
        return true;
    }

    public String[] getAIMove() {
        int max = -100000;
        int min = 100000;
        for(Map.Entry e : possibleMoves.entrySet()) {
            if(rulesSet.isVictoryConditionsReversed()) {
                if (((int) e.getValue()) < min)
                    min = (int) e.getValue();
            }
            else {
                if (((int) e.getValue()) > max)
                    max = (int) e.getValue();
            }
        }
        List<Move> moves = new ArrayList<>();
        for(Map.Entry e : possibleMoves.entrySet()) {
            if(rulesSet.isVictoryConditionsReversed()) {
                if (((int) e.getValue()) == min)
                    moves.add((Move) e.getKey());
            }
            else {
                if (((int) e.getValue()) == max)
                    moves.add((Move) e.getKey());
            }
        }
        Random r = new Random();
        Move bestMove = moves.get(r.nextInt(moves.size()));
        String[] s = new String[4];
        s[0] = "" + bestMove.getRow1();
        s[1] = "" + bestMove.getCol1();
        s[2] = "" + bestMove.getRow2();
        s[3] = "" + bestMove.getCol2();
        return s;
    }

}