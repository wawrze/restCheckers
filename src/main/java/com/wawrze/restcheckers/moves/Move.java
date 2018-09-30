package com.wawrze.restcheckers.moves;

import com.wawrze.restcheckers.board.*;
import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.FigureFactory;
import exceptions.*;

public class Move {

    private FigureFactory figureFactory = new FigureFactory();

    private char row1;
    private char row2;
    private int col1;
    private int col2;

    public Move(char row1, int col1, char row2, int col2) throws IncorrectMoveFormat {
        row1 = Character.toUpperCase(row1);
        row2 = Character.toUpperCase(row2);
        if (row1 == 'A' || row1 == 'B' || row1 == 'C' || row1 == 'D' || row1 == 'E' || row1 == 'F' || row1 == 'G'
                || row1 == 'H')
            this.row1 = row1;
        else
            throw new IncorrectMoveFormat();
        if (col1 <= 8 && col1 >= 1)
            this.col1 = col1;
        else
            throw new IncorrectMoveFormat();
        if (row2 == 'A' || row2 == 'B' || row2 == 'C' || row2 == 'D' || row2 == 'E' || row2 == 'F' || row2 == 'G'
                || row2 == 'H')
            this.row2 = row2;
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

    public void makeCapture(Board board, char row, int col){
        this.makeMove(board);
        board.setFigure(
                row,
                col,
                figureFactory.getNewFigure(board.getFigure(this.row1,this.col1).getColor(), Figure.NONE)
        );
    }

    @Override
    public String toString() {
        return "" + row1 + col1 + "-" + row2 + col2;
    }

}