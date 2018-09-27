package com.wawrze.restcheckers.figures;

import org.junit.*;

import java.util.LinkedList;
import java.util.List;

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
    public void testNoneToString() {
        //Given
        Figure none = new None(true);
        String s;
        //When
        s = "" + none;
        //Then
        Assert.assertEquals("  ",s);
    }

    @Test
    public void testPawnToString() {
        //Given
        Figure pawn1 = new Pawn(true);
        Figure pawn2 = new Pawn(false);
        String s1,s2;
        //When
        s1 = "" + pawn1;
        s2 = "" + pawn2;
        //Then
        Assert.assertEquals("PP",s1);
        Assert.assertEquals("pp",s2);
    }

    @Test
    public void testQueenToString() {
        //Given
        Figure queen1 = new Queen(true);
        Figure queen2 = new Queen(false);
        String s1,s2;
        //When
        s1 = "" + queen1;
        s2 = "" + queen2;
        //Then
        Assert.assertEquals("QQ",s1);
        Assert.assertEquals("qq",s2);
    }

    @Test
    public void testPrint() {
        //Given
        Figure pawn1 = new Pawn(true);
        Figure pawn2 = new Pawn(false);
        Figure queen1 = new Queen(true);
        Figure queen2 = new Queen(false);
        List<Figure> list = new LinkedList<>();
        //When
        list.add(pawn1);
        list.add(pawn2);
        list.add(queen1);
        list.add(queen2);
        for(Figure f : list) {
            for (int i = 0; i < 3; i++) {
                f.print(i);
            }
        }
        //Then
    }

}
