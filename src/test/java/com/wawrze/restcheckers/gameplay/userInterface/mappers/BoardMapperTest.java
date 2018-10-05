package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.BoardDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.FigureDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.RowDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardMapperTest {

    @Autowired
    BoardMapper boardMapper;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("BoardMapper tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("BoardMapper tests: finished");
    }

    @Before
    public void before(){
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after(){
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void testMapToBoardDto() {
        //Given
        Board board = new Board.BoardBuilder().build().getNewBoard();
        List<String> moves = new ArrayList<>();
        BoardDto boardDto = new BoardDto();
        //When
        moves.add("white: A1-B2");
        moves.add("white: C3-D4");
        moves.add("black: E5-F6");
        boardDto = boardMapper.mapToBoardDto(
                board
        );
        boardDto.getRows().add(new RowDto());
        boardDto.getRows().get(0).getFigures().add(new FigureDto());
        //Then
        assertEquals(
                board.getFigure('A', 1).getFigureName(),
                boardDto.getRows().get(0).getFigures().get(0).getName()
        );
        assertEquals(
                "true",
                boardDto.getRows().get(0).getFigures().get(0).getColor()
        );
        assertEquals(
                1,
                boardDto.getRows().get(0).getFigures().get(0).getCol()
        );
        assertEquals(
                board.getFigure('B', 3).getFigureName(),
                boardDto.getRows().get(1).getFigures().get(2).getName()
        );
        assertEquals(
                board.getFigure('C', 2).getFigureName(),
                boardDto.getRows().get(2).getFigures().get(1).getName()
        );
        assertEquals(
                board.getFigure('D', 4).getFigureName(),
                boardDto.getRows().get(3).getFigures().get(3).getName()
        );
        assertEquals(
                board.getFigure('E', 3).getFigureName(),
                boardDto.getRows().get(4).getFigures().get(2).getName()
        );
        assertEquals(
                board.getFigure('F', 5).getFigureName(),
                boardDto.getRows().get(5).getFigures().get(4).getName()
        );
        assertEquals(
                board.getFigure('G', 4).getFigureName(),
                boardDto.getRows().get(6).getFigures().get(3).getName()
        );
        assertEquals(
                board.getFigure('H', 6).getFigureName(),
                boardDto.getRows().get(7).getFigures().get(5).getName()
        );
    }

    @Test
    public void testMapToBoardDtoEmpty() {
        //Given
        Board board = new Board.BoardBuilder().build();
        BoardDto boardDto;
        //When
        boardDto = boardMapper.mapToBoardDto(
                board
        );
        //Then
        assertEquals(
                board.getFigure('A', 1).getFigureName(),
                boardDto.getRows().get(0).getFigures().get(0).getName()
        );
        assertEquals(
                board.getFigure('B', 3).getFigureName(),
                boardDto.getRows().get(1).getFigures().get(2).getName()
        );
        assertEquals(
                board.getFigure('C', 2).getFigureName(),
                boardDto.getRows().get(2).getFigures().get(1).getName()
        );
        assertEquals(
                board.getFigure('D', 4).getFigureName(),
                boardDto.getRows().get(3).getFigures().get(3).getName()
        );
        assertEquals(
                board.getFigure('E', 3).getFigureName(),
                boardDto.getRows().get(4).getFigures().get(2).getName()
        );
        assertEquals(
                board.getFigure('F', 5).getFigureName(),
                boardDto.getRows().get(5).getFigures().get(4).getName()
        );
        assertEquals(
                board.getFigure('G', 4).getFigureName(),
                boardDto.getRows().get(6).getFigures().get(3).getName()
        );
        assertEquals(
                board.getFigure('H', 6).getFigureName(),
                boardDto.getRows().get(7).getFigures().get(5).getName()
        );
    }

}