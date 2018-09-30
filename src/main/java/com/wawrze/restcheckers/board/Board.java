package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.FigureFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {

    private FigureFactory figureFactory = new FigureFactory();

    private Map<Character, BoardRow> rows;

    private Board(Map<Character, BoardRow> rows) {
        this.rows = rows;
    }

    public Board getNewBoard() {
        return new BoardBuilder()
                .addFigure('A', 2, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('A', 2, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('A', 4, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('A', 6, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('A', 8, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('B', 1, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('B', 3, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('B', 5, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('B', 7, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('C', 2, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('C', 4, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('C', 6, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('C', 8, figureFactory.getNewFigure(true, Figure.PAWN))
                .addFigure('F', 1, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('F', 3, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('F', 5, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('F', 7, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('G', 2, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('G', 4, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('G', 6, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('G', 8, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('H', 1, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('H', 3, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('H', 5, figureFactory.getNewFigure(false, Figure.PAWN))
                .addFigure('H', 7, figureFactory.getNewFigure(false, Figure.PAWN))
                .build();
    }

    public Board(Board board){
        rows = new HashMap<>();
        IntStream.iterate(65, i -> ++i)
                .limit(8)
                .forEach(i -> {
                    rows.put((char) i, new BoardRow((i % 2) == 1));
                    IntStream.iterate(1, j -> ++j)
                            .limit(8)
                            .forEach(j -> rows.get((char) i).setFigure(j, board.getFigure((char) i, j)));
                });
    }

    public static class BoardBuilder {

        private Map<Character, BoardRow> rows = new HashMap<Character, BoardRow>() {{
            put('A', new BoardRow(true));
            put('B', new BoardRow(false));
            put('C', new BoardRow(true));
            put('D', new BoardRow(false));
            put('E', new BoardRow(true));
            put('F', new BoardRow(false));
            put('G', new BoardRow(true));
            put('H', new BoardRow(false));
        }};

        public BoardBuilder addFigure(char row, int col, Figure figure) {
            this.rows.get(row).setFigure(col, figure);
            return this;
        }

        public Board build() {
            return new Board(this.rows);
        }

    }

    public Figure getFigure(char row, int col) {
        return this.rows.get(row).getFigure(col);
    }

    public void setFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
    }

    public Map<Character, BoardRow> getRows() {
        return rows;
    }

}