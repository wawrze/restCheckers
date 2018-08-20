package com.wawrze.restcheckers.gameplay.userInterfaces.mappers;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.Figure;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.BoardDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.FigureDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.RowDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardMapper {

    public BoardDto mapToBoardDto(final Board board, String gameStatus) {
        List<FigureDto> listRowA = new ArrayList<>();
        List<FigureDto> listRowB = new ArrayList<>();
        List<FigureDto> listRowC = new ArrayList<>();
        List<FigureDto> listRowD = new ArrayList<>();
        List<FigureDto> listRowE = new ArrayList<>();
        List<FigureDto> listRowF = new ArrayList<>();
        List<FigureDto> listRowG = new ArrayList<>();
        List<FigureDto> listRowH = new ArrayList<>();
        for(int i = 1;i < 9;i++) {
            listRowA.add(new FigureDto(
                    figureName(board.getFigure('A', i)),
                    i,
                    (board.getFigure('A', i).getColor()) ? "true" : "false")
            );
            listRowB.add(new FigureDto(
                    figureName(board.getFigure('B', i)),
                    i,
                    (board.getFigure('B', i).getColor()) ? "true" : "false")
            );
            listRowC.add(new FigureDto(
                    figureName(board.getFigure('C', i)),
                    i,
                    (board.getFigure('C', i).getColor()) ? "true" : "false")
            );
            listRowD.add(new FigureDto(
                    figureName(board.getFigure('D', i)),
                    i,
                    (board.getFigure('D', i).getColor()) ? "true" : "false")
            );
            listRowE.add(new FigureDto(
                    figureName(board.getFigure('E', i)),
                    i,
                    (board.getFigure('E', i).getColor()) ? "true" : "false")
            );
            listRowF.add(new FigureDto(
                    figureName(board.getFigure('F', i)),
                    i,
                    (board.getFigure('F', i).getColor()) ? "true" : "false")
            );
            listRowG.add(new FigureDto(
                    figureName(board.getFigure('G', i)),
                    i,
                    (board.getFigure('G', i).getColor()) ? "true" : "false")
            );
            listRowH.add(new FigureDto(
                    figureName(board.getFigure('H', i)),
                    i,
                    (board.getFigure('H', i).getColor()) ? "true" : "false")
            );
        }
        RowDto rowA = new RowDto(0, listRowA);
        RowDto rowB = new RowDto(1, listRowB);
        RowDto rowC = new RowDto(2, listRowC);
        RowDto rowD = new RowDto(3, listRowD);
        RowDto rowE = new RowDto(4, listRowE);
        RowDto rowF = new RowDto(5, listRowF);
        RowDto rowG = new RowDto(6, listRowG);
        RowDto rowH = new RowDto(7, listRowH);
        List<RowDto> rowDtos = new ArrayList<>();
        rowDtos.add(rowA);
        rowDtos.add(rowB);
        rowDtos.add(rowC);
        rowDtos.add(rowD);
        rowDtos.add(rowE);
        rowDtos.add(rowF);
        rowDtos.add(rowG);
        rowDtos.add(rowH);
        return new BoardDto(rowDtos, gameStatus);
    }

    private String figureName(Figure figure) {
        if(figure instanceof Pawn)
            return "pawn";
        else if(figure instanceof Queen)
            return "queen";
        return "none";
    }

}