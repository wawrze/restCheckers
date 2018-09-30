package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.Pawn;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {

    private Map<Character, BoardRow> rows;

    private Board(Map<Character, BoardRow> rows) {
        this.rows = rows;
    }

    public static Board getNewBoard() {
        return new BoardBuilder()
                .addFigure('A', 2, new Pawn(true))
                .addFigure('A', 4, new Pawn(true))
                .addFigure('A', 6, new Pawn(true))
                .addFigure('A', 8, new Pawn(true))
                .addFigure('B', 1, new Pawn(true))
                .addFigure('B', 3, new Pawn(true))
                .addFigure('B', 5, new Pawn(true))
                .addFigure('B', 7, new Pawn(true))
                .addFigure('C', 2, new Pawn(true))
                .addFigure('C', 4, new Pawn(true))
                .addFigure('C', 6, new Pawn(true))
                .addFigure('C', 8, new Pawn(true))
                .addFigure('F', 1, new Pawn(false))
                .addFigure('F', 3, new Pawn(false))
                .addFigure('F', 5, new Pawn(false))
                .addFigure('F', 7, new Pawn(false))
                .addFigure('G', 2, new Pawn(false))
                .addFigure('G', 4, new Pawn(false))
                .addFigure('G', 6, new Pawn(false))
                .addFigure('G', 8, new Pawn(false))
                .addFigure('H', 1, new Pawn(false))
                .addFigure('H', 3, new Pawn(false))
                .addFigure('H', 5, new Pawn(false))
                .addFigure('H', 7, new Pawn(false))
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