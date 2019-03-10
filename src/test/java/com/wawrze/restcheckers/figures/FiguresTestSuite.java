package com.wawrze.restcheckers.figures;

import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;
import org.junit.*;

public class FiguresTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("BoardRow tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("BoardRow tests: finished");
    }

    @Before
    public void before() {
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after() {
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void testGetFigureName() {
        //Given
        FigureFactory figureFactory = new FigureFactory();
        Figure pawn = figureFactory.getNewFigure(true, Figure.PAWN);
        Figure queen = figureFactory.getNewFigure(true, Figure.QUEEN);
        Figure none = figureFactory.getNewFigure(true, Figure.NONE);
        //When
        String result1 = pawn.getFigureName();
        String result2 = queen.getFigureName();
        String result3 = none.getFigureName();
        //Then
        Assert.assertEquals("pawn", result1);
        Assert.assertEquals("queen", result2);
        Assert.assertEquals("none", result3);
    }

}
