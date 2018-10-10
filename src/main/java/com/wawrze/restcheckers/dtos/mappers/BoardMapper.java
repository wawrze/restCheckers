package com.wawrze.restcheckers.dtos.mappers;

import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.dtos.BoardDto;
import com.wawrze.restcheckers.dtos.FigureDto;
import com.wawrze.restcheckers.dtos.RowDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class BoardMapper {

    public BoardDto mapToBoardDto(Board board) {
        List<RowDto> rowDtos = new ArrayList<>();
        IntStream.iterate(0, i -> ++i)
                .limit(8)
                .forEach(i -> {
                    List<FigureDto> listRow = new ArrayList<>();
                    IntStream.iterate(1, j -> ++j)
                            .limit(8)
                            .forEach(j -> listRow.add(new FigureDto(
                                    board.getFigure((char) (i + 65), j).getFigureName(),
                                    j,
                                    board.getFigure((char) (i + 65), j).getColor() ? "true" : "false"
                            )));
                    rowDtos.add(new RowDto(i, listRow));
                });
        return new BoardDto(rowDtos);
    }

}