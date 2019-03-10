package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.CapturesFinder;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CapturePossibilityValidator {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private MoveValidator moveValidator;

    @SuppressWarnings("Duplicates")
    private void findMaxCaptures(CapturesFinder capturesFinder) throws IncorrectMoveFormat {
        capturesFinder.setCounter(new int[capturesFinder.getListOfCaptures().size()]);
        for (String s : capturesFinder.getListOfCaptures()) {
            Board tmpBoard = new Board(capturesFinder.getBoard());
            String[] sArray = s.split("-");
            char x1 = sArray[0].charAt(0);
            int y1 = Character.getNumericValue(sArray[0].charAt(1));
            char x2 = sArray[1].charAt(0);
            int y2 = Character.getNumericValue(sArray[1].charAt(1));
            Move move;
            move = new Move(x1, y1, x2, y2);
            //noinspection EmptyFinallyBlock
            try {
                moveValidator.validateMove(move, tmpBoard, capturesFinder.isPlayer(), capturesFinder.getRulesSet());
            } catch (CaptureException e) {
                move.makeCapture(tmpBoard, e.getRow(), e.getCol());
            } catch (IncorrectMoveException ignored) {
            } finally {
            }
            CapturesFinder validator = new CapturesFinder(tmpBoard, capturesFinder.isPlayer(),
                    capturesFinder.getRulesSet());
            try {
                validateCapturePossibilityForOneFigure(validator, x2, y2);
                capturesFinder.getCounter()[capturesFinder.getListOfCaptures().indexOf(s)] = 1;
            } catch (CapturePossibleException e) {
                capturesFinder.getCounter()[capturesFinder.getListOfCaptures().indexOf(s)] +=
                        (validator.getMaxDepth() + 1);
            }
        }
        for (int i = (capturesFinder.getListOfCaptures().size() - 1); i >= 0; i--) {
            if (capturesFinder.getCounter()[i] < getMaxDepth(capturesFinder)) {
                capturesFinder.getListOfCaptures().remove(i);
            }
        }
    }

    private int getMaxDepth(CapturesFinder capturesFinder) {
        capturesFinder.setMaxDepth(0);
        for (int i : capturesFinder.getCounter())
            if (i > capturesFinder.getMaxDepth())
                capturesFinder.setMaxDepth(i);
        return capturesFinder.getMaxDepth();
    }

    public void validateCapturePossibility(CapturesFinder capturesFinder)
            throws CapturePossibleException {
        for (int i = 65; i < 73; i++) {
            for (int j = 1; j < 9; j++) {
                if (capturesFinder.getBoard().getFigure((char) i, j).getFigureName().equals(Figure.PAWN)
                        && capturesFinder.getBoard().getFigure((char) i, j).getColor() == capturesFinder.isPlayer()) {
                    validatePawnCapture(new ValidateDto(capturesFinder, (char) i, j, capturesFinder.getBoard()));
                } else if (capturesFinder.getBoard().getFigure((char) i, j).getFigureName().equals(Figure.QUEEN)
                        && capturesFinder.getBoard().getFigure((char) i, j).getColor() == capturesFinder.isPlayer()) {
                    validateQueenCapture(new ValidateDto(capturesFinder, (char) i, j, capturesFinder.getBoard()));
                }
            }
        }
        if (!capturesFinder.getRulesSet().isCaptureAny()) {
            try {
                findMaxCaptures(capturesFinder);
            } catch (IncorrectMoveFormat ignored) {
            }
        }
        listCheck(capturesFinder);
    }

    public void validateCapturePossibilityForOneFigure(CapturesFinder capturesFinder, char row, int col)
            throws CapturePossibleException {
        if (capturesFinder.getBoard().getFigure(row, col).getFigureName().equals(Figure.PAWN))
            validatePawnCapture(new ValidateDto(capturesFinder, row, col, capturesFinder.getBoard()));
        else
            validateQueenCapture(new ValidateDto(capturesFinder, row, col, capturesFinder.getBoard()));
        if (!capturesFinder.getRulesSet().isCaptureAny()) {
            try {
                findMaxCaptures(capturesFinder);
            } catch (IncorrectMoveFormat ignored) {
            }
        }
        listCheck(capturesFinder);
    }

    private void listCheck(CapturesFinder capturesFinder) throws CapturePossibleException {
        if (!capturesFinder.getListOfCaptures().isEmpty()) {
            StringBuilder m = new StringBuilder();
            for (String s : capturesFinder.getListOfCaptures())
                m.append(s).append(" ");
            throw new CapturePossibleException(m.toString());
        }
    }

    private void validateQueenCapture(ValidateDto validateData) {
        char row1 = validateData.row;
        int col1 = validateData.col;
        char row2;
        int col2;
        for (int i = 2; i < 9; i++) {
            //left-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 - i;
            try {
                addToCapturesIfItsCaptureQueen(row2, col2, validateData);
            } catch (IncorrectMoveFormat e) {
                break;
            }
        }
        for (int i = 2; i < 9; i++) {
            //right-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 + i;
            try {
                addToCapturesIfItsCaptureQueen(row2, col2, validateData);
            } catch (IncorrectMoveFormat e) {
                break;
            }
        }
        for (int i = 2; i < 9; i++) {
            //right-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 + i;
            try {
                addToCapturesIfItsCaptureQueen(row2, col2, validateData);
            } catch (IncorrectMoveFormat e) {
                break;
            }
        }
        for (int i = 2; i < 9; i++) {
            //left-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 - i;
            try {
                addToCapturesIfItsCaptureQueen(row2, col2, validateData);
            } catch (IncorrectMoveFormat e) {
                break;
            }
        }
    }

    private void addToCapturesIfItsCaptureQueen(char row2, int col2, ValidateDto validateData) throws IncorrectMoveFormat {
        CapturesFinder capturesFinder = validateData.capturesFinder;
        char row1 = validateData.row;
        int col1 = validateData.col;
        Board board = validateData.board;
        Move move;
        move = new Move(row1, col1, row2, col2);
        try {
            moveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(),
                    capturesFinder.getRulesSet());
        } catch (IncorrectMoveException ignored) {
        } catch (CaptureException e) {
            capturesFinder.getListOfCaptures().add("" + row1 + col1 + "-" + row2 + col2);
        }
    }

    private void validatePawnCapture(ValidateDto validateData) {
        char rowCaptureTo;
        int colCaptureTo;
        char rowCaptured;
        int colCaptured;
        //left-up
        rowCaptureTo = (char) (((int) validateData.row) - 2);
        colCaptureTo = validateData.col - 2;
        rowCaptured = (char) (((int) validateData.row) - 1);
        colCaptured = validateData.col - 1;
        addToCapturesIfItsCapturePawn(rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, validateData);
        //right-up
        rowCaptureTo = (char) (((int) validateData.row) - 2);
        colCaptureTo = validateData.col + 2;
        rowCaptured = (char) (((int) validateData.row) - 1);
        colCaptured = validateData.col + 1;
        addToCapturesIfItsCapturePawn(rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, validateData);
        //left-down
        rowCaptureTo = (char) (((int) validateData.row) + 2);
        colCaptureTo = validateData.col - 2;
        rowCaptured = (char) (((int) validateData.row) + 1);
        colCaptured = validateData.col - 1;
        addToCapturesIfItsCapturePawn(rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, validateData);
        //right-down
        rowCaptureTo = (char) (((int) validateData.row) + 2);
        colCaptureTo = validateData.col + 2;
        rowCaptured = (char) (((int) validateData.row) + 1);
        colCaptured = validateData.col + 1;
        addToCapturesIfItsCapturePawn(rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, validateData);
    }

    private void addToCapturesIfItsCapturePawn(char rowCaptureTo, int colCaptureTo, char rowCaptured, int colCaptured,
                                               ValidateDto validateData) {
        if (isOnBoard((int) rowCaptureTo, colCaptureTo) && isOnBoard((int) rowCaptured, colCaptured)) {
            if (validate(validateData.row, validateData.col, rowCaptureTo, colCaptureTo, rowCaptured, colCaptured, validateData.board)) {
                String moveToAdd = String.valueOf(validateData.row) +
                        validateData.col +
                        "-" +
                        rowCaptureTo +
                        colCaptureTo;
                validateData.capturesFinder.getListOfCaptures().add(moveToAdd);
            }
        }
    }

    private boolean isOnBoard(int row, int col) {
        return row > 64 && row < 73 && col > 0 && col < 9;
    }

    private boolean validate(char row1, int col1, char row2, int col2, char row3, int col3, Board board) {
        return !(board.getFigure(row3, col3).getFigureName().equals(Figure.NONE))
                && board.getFigure(row1, col1).getColor() != board.getFigure(row3, col3).getColor()
                && board.getFigure(row2, col2).getFigureName().equals(Figure.NONE);
    }

    @AllArgsConstructor
    private class ValidateDto {

        CapturesFinder capturesFinder;
        char row;
        int col;
        Board board;

    }

}