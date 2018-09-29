package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.*;

import java.util.ArrayList;
import java.util.List;

public class BoardRow {

	private List<Figure> figures;

    public BoardRow(boolean startColor) {
        this.figures = new ArrayList<>();
        //columns numeration 1-8, setting column 0 to null
        this.figures.add(0, null);
        for (int i = 1; i < 9; i++) {
            this.figures.add(i, new None(startColor));
            startColor = !startColor;
        }
    }

	public Figure getFigure(int col) {
		return figures.get(col);
	}

	public void setFigure(int col, Figure figure) {
		figures.set(col, figure);
	}

    public List<Figure> getFigures() {
        return figures;
    }

}