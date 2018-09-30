package com.wawrze.restcheckers.figures;

import org.junit.*;

public class FiguresTestSuite {

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
    public void after() {
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void testGetFigureName() {
        //Given
        Pawn pawn = new Pawn(true);
        Queen queen = new Queen(true);
        None none = new None(true);
        //When
        String result1 = pawn.getFigureName();
        String result2 = queen.getFigureName();
        String result3 = none.getFigureName();
        //Then
        Assert.assertEquals(result1, "pawn");
        Assert.assertEquals(result2, "queen");
        Assert.assertEquals(result3, "none");
    }

}
