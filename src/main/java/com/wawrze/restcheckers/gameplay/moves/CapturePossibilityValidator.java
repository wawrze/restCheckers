package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.RulesSet;
import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class CapturePossibilityValidator {

    private List<String> listOfCaptures;
    private Board board;
    private boolean player;
    private int[] counter;
    private int maxDepth;
    private RulesSet rulesSet;

    private void findMaxCaptures() {
        counter = new int[listOfCaptures.size()];
        for(String s : listOfCaptures){
            Board tmpBoard = new Board(this.board);
            String[] sArray = s.split("-");char x1 = sArray[0].charAt(0);
            int y1 = Character.getNumericValue(sArray[0].charAt(1));
            char x2 = sArray[1].charAt(0);
            int y2 = Character.getNumericValue(sArray[1].charAt(1));
            Move move = null;
            try {
                move = new Move(x1, y1, x2, y2);
            }
            catch (IncorrectMoveFormat e) {}
            try {
                MoveValidator.validateMove(move, tmpBoard, player, rulesSet);
            }
            catch (CaptureException | IncorrectMoveException e) {
                CaptureException exception = (CaptureException) e;
                move.makeCapture(tmpBoard, exception.getRow(), exception.getCol());
            }
            finally {}
            CapturePossibilityValidator validator = new CapturePossibilityValidator(tmpBoard, this.player, rulesSet);
            try {
                validator.validateCapturePossibilityForOneFigure(x2, y2);
                counter[listOfCaptures.indexOf(s)] = 1;
            }
            catch(CapturePossibleException e) {
                counter[listOfCaptures.indexOf(s)] += (validator.getMaxDepth() + 1);
            }
        }
        for(int i = (listOfCaptures.size() - 1);i >= 0;i--) {
            if (counter[i] < getMaxDepth()) {
                listOfCaptures.remove(i);
            }
        }
    }

    public int getMaxDepth() {
        maxDepth = 0;
        for(int i : counter)
            if(i > maxDepth)
                maxDepth = i;
        return maxDepth;
    }

    public CapturePossibilityValidator(Board board, boolean player, RulesSet rulesSet){
        this.listOfCaptures = new ArrayList<>();
        this.board = board;
        this.player = player;
        this.rulesSet = rulesSet;
    }

    public void validateCapturePossibility()
            throws CapturePossibleException {
        for(int i = 65;i<73;i++){
            for (int j = 1; j < 9; j++){
                if (board.getFigure((char) i, j).getFigureName().equals(Figure.PAWN)
                        && board.getFigure((char) i, j).getColor() == player) {
                    validatePawnCapture((char) i, j, board);
                }
                else if (board.getFigure((char) i, j).getFigureName().equals(Figure.QUEEN)
                        && board.getFigure((char) i, j).getColor() == player) {
                    validateQueenCapture((char) i, j, board);
                }
            }
        }
        if(!rulesSet.isCaptureAny()) {
            findMaxCaptures();
        }
        listCheck();
    }

    public void validateCapturePossibilityForOneFigure(char row,int col)
            throws CapturePossibleException {
        if(board.getFigure(row,col).getFigureName().equals(Figure.PAWN))
            validatePawnCapture(row,col,board);
        else
            validateQueenCapture(row,col,board);
        if(!rulesSet.isCaptureAny()) {
            findMaxCaptures();
        }
        listCheck();
    }

    private void listCheck() throws CapturePossibleException{
        if(!listOfCaptures.isEmpty()) {
            String m = "";
            for (String s : listOfCaptures)
                m += s + " ";
            throw new CapturePossibleException(m);
        }
    }

    private void validateQueenCapture(char row1, int col1, Board board){
        char row2;
        int col2;
        Move move = null;
        for(int i = 2;i<9;i++) {
            //left-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            }
            catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for(int i = 2;i<9;i++) {
            //right-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            }
            catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for(int i = 2;i<9;i++) {
            //right-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
            } catch (IncorrectMoveException e) {
            } catch (CaptureException e) {
                listOfCaptures.add("" + row1 + col1 + "-" + row2 + col2);
            }
        }
        for(int i = 2;i<9;i++) {
            //left-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            }
            catch(IncorrectMoveFormat e){
                break;
            }
            try{
                MoveValidator.validateMove(move,board,board.getFigure(row1,col1).getColor(), rulesSet);
            }
            catch(IncorrectMoveException e){
            }
            catch(CaptureException e){
                listOfCaptures.add("" + row1 + col1 + "-" + row2  + col2);
            }
        }
    }

    private void validatePawnCapture(char row,int col, Board board){
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        //left-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //left-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo,colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row,col,rowCaptureTo,colCaptureTo,rowCaptured,colCaptured,board))
                listOfCaptures.add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
    }

    private boolean isOnBoard(int row,int col){
        if(row > 64 && row < 73 && col > 0 && col < 9)
            return true;
        else return false;
    }

    private boolean validate(char row1,int col1,char row2, int col2, char row3, int col3, Board board){
        if(!(board.getFigure(row3,col3).getFigureName().equals(Figure.NONE))
                && board.getFigure(row1,col1).getColor() != board.getFigure(row3,col3).getColor()
                && board.getFigure(row2,col2).getFigureName().equals(Figure.NONE)) {
            return true;
        }
        else {
            return false;
        }
    }

}
