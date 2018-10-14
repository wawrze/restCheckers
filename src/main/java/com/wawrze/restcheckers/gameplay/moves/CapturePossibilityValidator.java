package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.CapturesFinder;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class CapturePossibilityValidator {

    private void findMaxCaptures(CapturesFinder capturesFinder) {
        capturesFinder.setCounter(new int[capturesFinder.getListOfCaptures().size()]);
        for(String s : capturesFinder.getListOfCaptures()){
            Board tmpBoard = new Board(capturesFinder.getBoard());
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
                MoveValidator.validateMove(move, tmpBoard, capturesFinder.isPlayer(), capturesFinder.getRulesSet());
            }
            catch (CaptureException | IncorrectMoveException e) {
                CaptureException exception = (CaptureException) e;
                move.makeCapture(tmpBoard, exception.getRow(), exception.getCol());
            }
            finally {}
            CapturesFinder validator = new CapturesFinder(tmpBoard, capturesFinder.isPlayer(),
                    capturesFinder.getRulesSet());
            try {
                validateCapturePossibilityForOneFigure(validator, x2, y2);
                capturesFinder.getCounter()[capturesFinder.getListOfCaptures().indexOf(s)] = 1;
            }
            catch(CapturePossibleException e) {
                capturesFinder.getCounter()[capturesFinder.getListOfCaptures().indexOf(s)] +=
                        (validator.getMaxDepth() + 1);
            }
        }
        for(int i = (capturesFinder.getListOfCaptures().size() - 1);i >= 0;i--) {
            if (capturesFinder.getCounter()[i] < getMaxDepth(capturesFinder)) {
                capturesFinder.getListOfCaptures().remove(i);
            }
        }
    }

    public int getMaxDepth(CapturesFinder capturesFinder) {
        capturesFinder.setMaxDepth(0);
        for(int i : capturesFinder.getCounter())
            if(i > capturesFinder.getMaxDepth())
                capturesFinder.setMaxDepth(i);
        return capturesFinder.getMaxDepth();
    }

    public void validateCapturePossibility(CapturesFinder capturesFinder)
            throws CapturePossibleException {
        for(int i = 65;i<73;i++){
            for(int j = 1; j < 9; j++){
                if (capturesFinder.getBoard().getFigure((char) i, j).getFigureName().equals(Figure.PAWN)
                        && capturesFinder.getBoard().getFigure((char) i, j).getColor() == capturesFinder.isPlayer()) {
                    validatePawnCapture(capturesFinder, (char) i, j, capturesFinder.getBoard());
                }
                else if(capturesFinder.getBoard().getFigure((char) i, j).getFigureName().equals(Figure.QUEEN)
                        && capturesFinder.getBoard().getFigure((char) i, j).getColor() == capturesFinder.isPlayer()) {
                    validateQueenCapture(capturesFinder, (char) i, j, capturesFinder.getBoard());
                }
            }
        }
        if(!capturesFinder.getRulesSet().isCaptureAny()) {
            findMaxCaptures(capturesFinder);
        }
        listCheck(capturesFinder);
    }

    public void validateCapturePossibilityForOneFigure(CapturesFinder capturesFinder, char row, int col)
            throws CapturePossibleException {
        if(capturesFinder.getBoard().getFigure(row, col).getFigureName().equals(Figure.PAWN))
            validatePawnCapture(capturesFinder, row, col, capturesFinder.getBoard());
        else
            validateQueenCapture(capturesFinder, row, col, capturesFinder.getBoard());
        if(!capturesFinder.getRulesSet().isCaptureAny()) {
            findMaxCaptures(capturesFinder);
        }
        listCheck(capturesFinder);
    }

    private void listCheck(CapturesFinder capturesFinder) throws CapturePossibleException{
        if(!capturesFinder.getListOfCaptures().isEmpty()) {
            String m = "";
            for (String s : capturesFinder.getListOfCaptures())
                m += s + " ";
            throw new CapturePossibleException(m);
        }
    }

    private void validateQueenCapture(CapturesFinder capturesFinder, char row1, int col1, Board board){
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(),
                        capturesFinder.getRulesSet());
            } catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                capturesFinder.getListOfCaptures().add("" + row1 + col1 + "-" + row2 + col2);
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(),
                        capturesFinder.getRulesSet());
            }
            catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                capturesFinder.getListOfCaptures().add("" + row1 + col1 + "-" + row2 + col2);
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(),
                        capturesFinder.getRulesSet());
            } catch (IncorrectMoveException e) {
            } catch (CaptureException e) {
                capturesFinder.getListOfCaptures().add("" + row1 + col1 + "-" + row2 + col2);
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
                MoveValidator.validateMove(move,board,board.getFigure(row1,col1).getColor(),
                        capturesFinder.getRulesSet());
            }
            catch(IncorrectMoveException e){
            }
            catch(CaptureException e){
                capturesFinder.getListOfCaptures().add("" + row1 + col1 + "-" + row2  + col2);
            }
        }
    }

    private void validatePawnCapture(CapturesFinder capturesFinder, char row,int col, Board board) {
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        //left-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                capturesFinder.getListOfCaptures().add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-up
        rowCaptureTo = (char) (((int) row) - 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) - 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                capturesFinder.getListOfCaptures().add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //left-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col - 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col - 1;
        if(isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                capturesFinder.getListOfCaptures().add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
        //right-down
        rowCaptureTo = (char) (((int) row) + 2);
        colCaptureTo = col + 2;
        rowCaptured = (char) (((int) row) + 1);
        colCaptured = col + 1;
        if(isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured))
            if(validate(row, col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, board))
                capturesFinder.getListOfCaptures().add("" + row + col + "-" + rowCaptureTo  + colCaptureTo);
    }

    private boolean isOnBoard(int row, int col) {
        if(row > 64 && row < 73 && col > 0 && col < 9)
            return true;
        else return false;
    }

    private boolean validate(char row1,int col1,char row2, int col2, char row3, int col3, Board board) {
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
