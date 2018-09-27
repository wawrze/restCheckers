package com.wawrze.restcheckers.board;

import com.wawrze.restcheckers.figures.*;
import org.junit.*;

public class BoardTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("Board tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("Board tests: finished");
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
        Board board = new Board();
        boolean result = true;
        //When
        for(int i = 65;i<73;i++)
            result = result && (board.getFigure((char) i,1) instanceof None);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSetGetFigure(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(false);
        Queen queen = new Queen(false);
        None none = new None(true);
        //When
        board.setFigure('A',1, pawn1);
        board.setFigure('B',2, pawn2);
        board.setFigure('C',3, queen);
        board.setFigure('B',2, none);
        //then
        Assert.assertEquals(pawn1,board.getFigure('A',1));
        Assert.assertEquals(none,board.getFigure('B',2));
        Assert.assertEquals(queen,board.getFigure('C',3));
    }

}
