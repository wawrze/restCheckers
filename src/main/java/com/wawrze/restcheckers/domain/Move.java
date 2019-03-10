package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;
import exceptions.IncorrectMoveFormat;

public class Move {

    private FigureFactory figureFactory = new FigureFactory();

    private final char row1;
    private final char row2;
    private final int col1;
    private final int col2;

    public Move(final char row1, final int col1, final char row2, final int col2) throws IncorrectMoveFormat {
        char row1u = Character.toUpperCase(row1);
        char row2u = Character.toUpperCase(row2);
        if (row1u == 'A' || row1u == 'B' || row1u == 'C' || row1u == 'D' || row1u == 'E' || row1u == 'F' || row1u == 'G'
                || row1u == 'H')
            this.row1 = row1u;
        else
            throw new IncorrectMoveFormat();
        if (col1 <= 8 && col1 >= 1)
            this.col1 = col1;
        else
            throw new IncorrectMoveFormat();
        if (row2u == 'A' || row2u == 'B' || row2u == 'C' || row2u == 'D' || row2u == 'E' || row2u == 'F' || row2u == 'G'
                || row2u == 'H')
            this.row2 = row2u;
        else
            throw new IncorrectMoveFormat();
        if (col2 <= 8 && col2 >= 1)
            this.col2 = col2;
        else
            throw new IncorrectMoveFormat();
    }

    public char getRow1() {
        return this.row1;
    }

    public int getRow1int() {
        return ((int) row1) - 64;
    }

    public int getCol1() {
        return this.col1;
    }

    public char getRow2() {
        return this.row2;
    }

    public int getRow2int() {
        return ((int) row2) - 64;
    }

    public int getCol2() {
        return this.col2;
    }

    public void makeMove(Board board) {
        board.setFigure(this.row2, this.col2, board.getFigure(this.row1, this.col1));
        board.setFigure(this.row1, this.col1, figureFactory.getNewFigure(false, Figure.NONE));
    }

    public void makeCapture(Board board, char row, int col) {
        this.makeMove(board);
        board.setFigure(
                row,
                col,
                figureFactory.getNewFigure(board.getFigure(this.row1, this.col1).getColor(), Figure.NONE)
        );
    }

    @Override
    public String toString() {
        return "" + row1 + col1 + "-" + row2 + col2;
    }

}