package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.None;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.moves.CapturePossibilityValidator;
import com.wawrze.restcheckers.moves.Move;
import com.wawrze.restcheckers.moves.MoveValidator;
import exceptions.*;

import java.util.*;

public class AIPlayer1 {

    private final int MAX_DEPTH = 3;

    private Board board;
    private boolean AIPlayer;
    private boolean activePlayer;
    private RulesSet rulesSet;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private int depth;
    private Map<Move,Integer> possibleMoves;

    public AIPlayer1(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves)
            throws IncorrectMoveFormat, IncorrectMoveException {
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

    public AIPlayer1(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves, int blackQueenMoves, char row,
                     int col) throws IncorrectMoveFormat, IncorrectMoveException {
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

    private AIPlayer1(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                      int blackQueenMoves, int depth) throws IncorrectMoveFormat, IncorrectMoveException {
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

    private AIPlayer1(Board board, boolean AIPlayer, boolean activePlayer, RulesSet rulesSet, int whiteQueenMoves,
                      int blackQueenMoves, int depth, char row, int col)
            throws IncorrectMoveFormat, IncorrectMoveException {
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

    private void evaluateMoves() throws IncorrectMoveFormat, IncorrectMoveException {
        Map<Move,Integer> moves = new HashMap<>(possibleMoves);
        boolean capture;
        int value;
        for(Map.Entry<Move,Integer> entry : moves.entrySet()){
            Board tmpBoard = new Board(board);
            capture = false;
            try{
                MoveValidator.validateMove(entry.getKey(), tmpBoard, activePlayer, rulesSet);
                entry.getKey().makeMove(tmpBoard);
                if(tmpBoard.getFigure(entry.getKey().getRow2(), entry.getKey().getCol2()) instanceof Queen){
                    if(activePlayer)
                        blackQueenMoves++;
                    else
                        whiteQueenMoves++;
                }
                if(VictoryValidator.validateEndOfGame(tmpBoard, whiteQueenMoves, blackQueenMoves, activePlayer,
                        rulesSet)) {
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
                    if(VictoryValidator.validateEndOfGame(tmpBoard, whiteQueenMoves, blackQueenMoves, activePlayer,
                            rulesSet)) {
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
            if(activePlayer != AIPlayer)
                value *= -1;
            value += getFigureSetEvaluation(tmpBoard);
            if(depth < MAX_DEPTH){
                if(capture) {
                    AIPlayer1 next_move = new AIPlayer1(tmpBoard, AIPlayer, activePlayer, rulesSet, whiteQueenMoves,
                            blackQueenMoves, depth, entry.getKey().getRow2(), entry.getKey().getCol2());
                    value += next_move.getMovesMapValue();
                }
                else {
                    AIPlayer1 next_move = new AIPlayer1(tmpBoard, AIPlayer, !activePlayer, rulesSet, whiteQueenMoves,
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

    private void getPossibleMovesMultiCapture(char row, int col) throws IncorrectMoveFormat, IncorrectMoveException {
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

    private void getPossibleMoves() throws IncorrectMoveFormat, IncorrectMoveException {
        try {
            (new CapturePossibilityValidator(board, activePlayer, rulesSet)).validateCapturePossibility();
            for(int i = 1;i<9;i++) {
                if (!(board.getFigure('A', i) instanceof None) && board.getFigure('A', i).getColor() == activePlayer){
                    getFigureMovePossibility('A', i);
                }
                if (!(board.getFigure('B', i) instanceof None) && board.getFigure('B', i).getColor() == activePlayer){
                    getFigureMovePossibility('B', i);
                }
                if (!(board.getFigure('C', i) instanceof None) && board.getFigure('C', i).getColor() == activePlayer){
                    getFigureMovePossibility('C', i);
                }
                if (!(board.getFigure('D', i) instanceof None) && board.getFigure('D', i).getColor() == activePlayer){
                    getFigureMovePossibility('D', i);
                }
                if (!(board.getFigure('E', i) instanceof None) && board.getFigure('E', i).getColor() == activePlayer){
                    getFigureMovePossibility('E', i);
                }
                if (!(board.getFigure('F', i) instanceof None) && board.getFigure('F', i).getColor() == activePlayer){
                    getFigureMovePossibility('F', i);
                }
                if (!(board.getFigure('G', i) instanceof None) && board.getFigure('G', i).getColor() == activePlayer){
                    getFigureMovePossibility('G', i);
                }
                if (!(board.getFigure('H', i) instanceof None) && board.getFigure('H', i).getColor() == activePlayer){
                    getFigureMovePossibility('H', i);
                }
            }
        }
        catch(CapturePossibleException e) {
            moveListFromCaptures(e.getMessage());
        }
    }

    private int getFigureSetEvaluation(Board board) {
        int value = 0;
        for(int i = 1;i<9;i++) {
            if (!(board.getFigure('A', i) instanceof None)) {
                if (board.getFigure('A', i) instanceof Queen)
                    value += board.getFigure('A', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('A', i).getColor() ? -1 : 8;
            }
            if (!(board.getFigure('B', i) instanceof None)) {
                if (board.getFigure('B', i) instanceof Queen)
                    value += board.getFigure('B', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('B', i).getColor() ? -2 : 7;
            }
            if (!(board.getFigure('C', i) instanceof None)) {
                if (board.getFigure('C', i) instanceof Queen)
                    value += board.getFigure('C', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('C', i).getColor() ? -3 : 6;
            }
            if (!(board.getFigure('D', i) instanceof None)) {
                if (board.getFigure('D', i) instanceof Queen)
                    value += board.getFigure('D', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('D', i).getColor() ? -4 : 5;
            }
            if (!(board.getFigure('E', i) instanceof None)) {
                if (board.getFigure('E', i) instanceof Queen)
                    value += board.getFigure('E', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('E', i).getColor() ? -5 : 4;
            }
            if (!(board.getFigure('F', i) instanceof None)) {
                if(board.getFigure('F', i) instanceof Queen)
                    value += board.getFigure('F', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('F', i).getColor() ? -6 : 3;
            }
            if (!(board.getFigure('G', i) instanceof None)) {
                if(board.getFigure('G', i) instanceof Queen)
                    value += board.getFigure('G', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('G', i).getColor() ? -7 : 2;
            }
            if (!(board.getFigure('H', i) instanceof None)) {
                if(board.getFigure('H', i) instanceof Queen)
                    value += board.getFigure('H', i).getColor() ? -80 : 80;
                else
                    value += board.getFigure('H', i).getColor() ? -8 : 1;
            }
        }
        if(activePlayer != AIPlayer)
            value *= -1;
        return value;
    }

    private void getFigureMovePossibility(char row, int col) {
        int range = 8;
        if(board.getFigure(row,col) instanceof Pawn || rulesSet.isQueenRangeOne())
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