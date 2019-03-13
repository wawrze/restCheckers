package com.wawrze.restcheckers.domain.board;

import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Entity
@Table(name = "boards")
@NoArgsConstructor
public class Board {

    private Long id;

    private FigureFactory figureFactory = new FigureFactory();

    private Map<Character, BoardRow> rows;

    private Board(Map<Character, BoardRow> rows) {
        this.rows = rows;
    }

    public Board(Board board) {
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

    @Transient
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

    @Transient
    public Figure getFigure(char row, int col) {
        return this.rows.get(row).getFigure(col);
    }

    public void setFigure(char row, int col, Figure figure) {
        this.rows.get(row).setFigure(col, figure);
    }

    @Transient
    public Map<Character, BoardRow> getRows() {
        return rows;
    }

    @Column(name = "id")
    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    @Column(name = "string_representation")
    public String getStringRepresentation() {
        String result = "";
        for (int i = 65; i < 73; i++) {
            for (int j = 1; j < 9; j++) {
                Figure f = rows.get((char) i).getFigures().get(j);
                if (f.getFigureName().equals(Figure.PAWN))
                    result += "p";
                else if (f.getFigureName().equals(Figure.QUEEN))
                    result += "q";
                else
                    result += "n";
                if (f.getColor())
                    result += "b";
                else
                    result += "w";
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    public void setStringRepresentation(String stringRepresentation) {
        char[] string = stringRepresentation.toCharArray();
        Board board = new Board.BoardBuilder().build();
        int c = 0;
        for (int i = 65; i < 73; i++) {
            for (int j = 1; j < 9; j++) {
                Figure f;
                if (string[c] == 'p') {
                    c++;
                    if (string[c] == 'b')
                        f = figureFactory.getNewFigure(true, Figure.PAWN);
                    else
                        f = figureFactory.getNewFigure(false, Figure.PAWN);
                    c++;
                } else if (string[c] == 'q') {
                    c++;
                    if (string[c] == 'b')
                        f = figureFactory.getNewFigure(true, Figure.QUEEN);
                    else
                        f = figureFactory.getNewFigure(false, Figure.QUEEN);
                    c++;
                } else {
                    c++;
                    if (string[c] == 'b')
                        f = figureFactory.getNewFigure(true, Figure.NONE);
                    else
                        f = figureFactory.getNewFigure(false, Figure.NONE);
                    c++;
                }
                board.setFigure((char) i, j, f);
            }
        }
        this.rows = board.getRows();
    }

    public static class BoardBuilder {

        private Map<Character, BoardRow> rows;

        public BoardBuilder() {
            rows = new HashMap<>();
            rows.put('A', new BoardRow(true));
            rows.put('B', new BoardRow(false));
            rows.put('C', new BoardRow(true));
            rows.put('D', new BoardRow(false));
            rows.put('E', new BoardRow(true));
            rows.put('F', new BoardRow(false));
            rows.put('G', new BoardRow(true));
            rows.put('H', new BoardRow(false));
        }

        public BoardBuilder addFigure(char row, int col, Figure figure) {
            this.rows.get(row).setFigure(col, figure);
            return this;
        }

        public Board build() {
            return new Board(this.rows);
        }

    }

}