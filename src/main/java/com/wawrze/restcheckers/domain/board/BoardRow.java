package com.wawrze.restcheckers.domain.board;

import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;

import java.util.ArrayList;
import java.util.List;

public final class BoardRow {

    private FigureFactory figureFactory = new FigureFactory();

	private final List<Figure> figures;

    public BoardRow(boolean startColor) {
        this.figures = new ArrayList<>();
        //columns numeration 1-8, setting column 0 to null
        this.figures.add(0, null);
        for (int i = 1; i < 9; i++) {
            this.figures.add(i, figureFactory.getNewFigure(startColor, Figure.NONE));
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