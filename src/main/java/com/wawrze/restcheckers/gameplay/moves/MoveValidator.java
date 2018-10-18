package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.RulesSet;
import exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class MoveValidator {

    private static final String SFOTW = "Some figure on the way!";

    public void validateMove(Move move, Board board, boolean player, RulesSet rulesSet)
            throws CaptureException, IncorrectMoveException {
        validateBias(move);
        validateField1(move, board);
        validateField2(move, board);
        validatePlayer(move, board, player);
        if (board.getFigure(move.getRow1(), move.getCol1()).getFigureName().equals(Figure.PAWN))
            validatePawnMove(move, board, player, rulesSet);
        else if (board.getFigure(move.getRow1(), move.getCol1()).getFigureName().equals(Figure.QUEEN))
            validateQueenMove(move, board, player, rulesSet);
    }

    private void validateField1(Move move, Board board) throws IncorrectMoveException {
        if (board.getFigure(move.getRow1(), move.getCol1()).getFigureName().equals(Figure.NONE))
            throw new IncorrectMoveException("No figure to move!");
    }

    private void validateField2(Move move, Board board) throws IncorrectMoveException {
        if (!(board.getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.NONE)))
            throw new IncorrectMoveException("Target field is occupied!");
    }

    private void validateBias(Move move) throws IncorrectMoveException {
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        if (Math.abs(x1 - x2) != Math.abs(y1 - y2))
            throw new IncorrectMoveException("Fields are not bias!");
    }

    private void validatePlayer(Move move, Board board, boolean player) throws IncorrectMoveException {
        if (board.getFigure(move.getRow1(), move.getCol1()).getColor() != player)
            throw new IncorrectMoveException("Not your figure!");
    }

    private void validatePawnMove(Move move, Board board, boolean player, RulesSet rulesSet)
            throws CaptureException, IncorrectMoveException {
        if(!rulesSet.isPawnCaptureBackward()) {
            validateDirection(move, player);
            validateRange(move, board);
        }
        else {
            validateRange(move, board);
            if (!rulesSet.isPawnMoveBackward())
                validateDirection(move, player);
        }
    }

    private void validateRange(Move move, Board board) throws CaptureException, IncorrectMoveException {
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        if ((Math.abs(x1 - x2) == 2) && (Math.abs(y1 - y2) == 2)) {
            char x = (char) (((x1 + x2) / 2) + 64);
            int y = ((y1 + y2) / 2);
            if (!(board.getFigure(x, y).getFigureName().equals(Figure.NONE))
                    && board.getFigure(x, y).getColor() != board.getFigure(move.getRow1(), move.getCol1()).getColor()) {
                throw new CaptureException(x, y);
            }
            else {
                throw new IncorrectMoveException("Invalid range!");
            }
        } else if ((Math.abs(x1 - x2) != 1) || (Math.abs(y1 - y2) != 1))
            throw new IncorrectMoveException("Invalid range!");
    }

    private void validateDirection(Move move, boolean player) throws IncorrectMoveException {
        if (player) {
            if ((move.getRow2int() - move.getRow1int()) < 0)
                throw new IncorrectMoveException("Invalid direction!");
        } else {
            if ((move.getRow2int() - move.getRow1int()) > 0)
                throw new IncorrectMoveException("Invalid direction!");
        }
    }

    private void validateQueenMove(Move move, Board board, boolean player, RulesSet rulesSet)
            throws IncorrectMoveException, CaptureException {
        if(rulesSet.isQueenRangeOne())
            validateRange(move, board);
        validateOnWay(move, board, player);
    }

    private void validateOnWay(Move move, Board board, boolean player) throws IncorrectMoveException,
            CaptureException {
        int x1 = move.getRow1int();
        int y1 = move.getCol1();
        int x2 = move.getRow2int();
        int y2 = move.getCol2();
        char x = 0;
        int y = 0;
        int counter = 0;
        Figure figure;
        //left-up
        if (x1 > x2 && y1 > y2) {
            for (int i = 1; i < (x1 - x2 - 1); i++) {
                figure = board.getFigure((char) (64 + x1 - i), y1 - i);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x1 - i);
                        y = y1 - i;
                        counter++;
                    }
                }
            }
            if (y1 - y2 > 1) {
                figure = board.getFigure((char) (64 + x2 + 1), y1 - x1 + x2 + 1);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + +x2 + 1);
                        y = y1 - x1 + x2 + 1;
                        counter++;
                    }
                }
            }
            if (counter == 1)
                throw new CaptureException(x, y);
            if (counter != 0)
                throw new IncorrectMoveException(SFOTW);
        }
        //right-up
        else if (x1 > x2 && y1 < y2) {
            for (int i = 1; i < (x1 - x2 - 1); i++) {
                figure = board.getFigure((char) (64 + x1 - i), y1 + i);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x1 - i);
                        y = y1 + i;
                        counter++;
                    }
                }
            }
            if (y2 - y1 > 1) {
                figure = board.getFigure((char) (64 + x2 + 1), y1 + x1 - x2 - 1);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + +x2 + 1);
                        y = y1 + x1 - x2 - 1;
                        counter++;
                    }
                }
            }
            if (counter == 1)
                throw new CaptureException(x, y);
            if (counter != 0)
                throw new IncorrectMoveException(SFOTW);
        }
        //left-down
        else if (x1 < x2 && y1 > y2) {
            for (int i = 1; i < (x2 - x1 - 1); i++) {
                figure = board.getFigure((char) (64 + x1 + i), y1 - i);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x1 + i);
                        y = y1 - i;
                        counter++;
                    }
                }
            }
            if (y1 - y2 > 1) {
                figure = board.getFigure((char) (64 + x2 - 1), y1 - x2 + x1 + 1);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x2 - 1);
                        y = y1 - x2 + x1 + 1;
                        counter++;
                    }
                }
            }
            if (counter == 1)
                throw new CaptureException(x, y);
            if (counter != 0)
                throw new IncorrectMoveException(SFOTW);
        }
        //right-down
        else if (x1 < x2 && y1 < y2) {
            for (int i = 1; i < (x2 - x1 - 1); i++) {
                figure = board.getFigure((char) (64 + x1 + i), y1 + i);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x1 + i);
                        y = y1 + i;
                        counter++;
                    }
                }
            }
            if (y2 - y1 > 1) {
                figure = board.getFigure((char) (64 + x2 - 1), y1 + x2 - x1 - 1);
                if (!(figure.getFigureName().equals(Figure.NONE))) {
                    if (figure.getColor() == player)
                        throw new IncorrectMoveException(SFOTW);
                    else {
                        x = (char) (64 + x2 - 1);
                        y = y1 + x2 - x1 - 1;
                        counter++;
                    }
                }
            }
            if (counter == 1)
                throw new CaptureException(x, y);
            if (counter != 0)
                throw new IncorrectMoveException(SFOTW);
        }
    }
}