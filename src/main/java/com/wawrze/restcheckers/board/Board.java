package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.Figure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Board implements Serializable {

	private Map<Character, BoardRow> rows;

	public Board() {
		this.rows = new HashMap<>();
        rows.put('A', new BoardRow(true));
        rows.put('B', new BoardRow(false));
        rows.put('C', new BoardRow(true));
        rows.put('D', new BoardRow(false));
        rows.put('E', new BoardRow(true));
        rows.put('F', new BoardRow(false));
        rows.put('G', new BoardRow(true));
        rows.put('H', new BoardRow(false));
	}

	public Board(Board board){
        rows = new HashMap<>();
        rows.put('A', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('A').setFigure(i,board.getFigure('A',i));
        rows.put('B', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('B').setFigure(i,board.getFigure('B',i));
        rows.put('C', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('C').setFigure(i,board.getFigure('C',i));
        rows.put('D', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('D').setFigure(i,board.getFigure('D',i));
        rows.put('E', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('E').setFigure(i,board.getFigure('E',i));
        rows.put('F', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('F').setFigure(i,board.getFigure('F',i));
        rows.put('G', new BoardRow(true));
        for(int i = 1;i<9;i++)
            rows.get('G').setFigure(i,board.getFigure('G',i));
        rows.put('H', new BoardRow(false));
        for(int i = 1;i<9;i++)
            rows.get('H').setFigure(i,board.getFigure('H',i));
    }

    public Figure getFigure(char row, int col) {
		return this.rows.get(row).getFigure(col);
	}

	public void setFigure(char row, int col, Figure figure) {
		this.rows.get(row).setFigure(col, figure);
	}

}