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
                    board.getFigure('A', i).getColor())
            );
            listRowB.add(new FigureDto(
                    figureName(board.getFigure('B', i)),
                    board.getFigure('B', i).getColor())
            );
            listRowC.add(new FigureDto(
                    figureName(board.getFigure('C', i)),
                    board.getFigure('C', i).getColor())
            );
            listRowD.add(new FigureDto(
                    figureName(board.getFigure('D', i)),
                    board.getFigure('D', i).getColor())
            );
            listRowE.add(new FigureDto(
                    figureName(board.getFigure('E', i)),
                    board.getFigure('E', i).getColor())
            );
            listRowF.add(new FigureDto(
                    figureName(board.getFigure('F', i)),
                    board.getFigure('F', i).getColor())
            );
            listRowG.add(new FigureDto(
                    figureName(board.getFigure('G', i)),
                    board.getFigure('G', i).getColor())
            );
            listRowH.add(new FigureDto(
                    figureName(board.getFigure('H', i)),
                    board.getFigure('H', i).getColor())
            );
        }
        RowDto rowA = new RowDto('A', listRowA);
        RowDto rowB = new RowDto('B', listRowB);
        RowDto rowC = new RowDto('C', listRowC);
        RowDto rowD = new RowDto('D', listRowD);
        RowDto rowE = new RowDto('E', listRowE);
        RowDto rowF = new RowDto('F', listRowF);
        RowDto rowG = new RowDto('G', listRowG);
        RowDto rowH = new RowDto('H', listRowH);
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