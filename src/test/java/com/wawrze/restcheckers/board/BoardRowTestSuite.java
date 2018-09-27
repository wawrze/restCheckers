package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.*;
import org.junit.*;

public class BoardRowTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("BoardRow tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("BoardRow tests: finished");
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
    public void testConstructor(){
        //Given
        BoardRow boardRow = new BoardRow(true);
        boolean result = true;
        //When
        for(int i = 1;i < 9;i++)
                result = result && (boardRow.getFigure(i) instanceof None);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSetGetFigure(){
        //Given
        BoardRow boardRow = new BoardRow(true);
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        Queen queen = new Queen(false);
        None none = new None(true);
        //When
        boardRow.setFigure(1, pawn1);
        boardRow.setFigure(2, pawn2);
        boardRow.setFigure(3, queen);
        boardRow.setFigure(2, none);
        //Then
        Assert.assertEquals(pawn1, boardRow.getFigure(1));
        Assert.assertEquals(queen, boardRow.getFigure(3));
        Assert.assertEquals(none, boardRow.getFigure(2));
    }

}