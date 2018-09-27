package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import org.junit.*;

public class VictoryValidatorTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("CapturePossibilityValidator tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("CapturePossibilityValidator tests: finished");
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

    /*******************
     * REVERSED RULES *
     ******************/

    @Test
    public void testNoEndOfGame1rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Queen queen4 = new Queen(true);
        Queen queen1 = new Queen(true);
        Queen queen3 = new Queen(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(false);
        Pawn pawn11 = new Pawn(false);
        Pawn pawn12 = new Pawn(false);
        Pawn pawn13 = new Pawn(false);
        Pawn pawn14 = new Pawn(false);
        Queen queen2 = new Queen(true);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('B',3,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('D',5,pawn4);
        board.setFigure('E',4,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',5,queen4);
        board.setFigure('G',8,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',1,pawn9);
        board.setFigure('C',6,pawn10);
        board.setFigure('D',7,pawn11);
        board.setFigure('E',2,pawn12);
        board.setFigure('F',5,pawn13);
        board.setFigure('H',1,pawn14);
        board.setFigure('A',2,queen2);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame2rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(false);
        Queen queen2 = new Queen(true);
        Pawn pawn4 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('A',2,queen1);
        board.setFigure('C',6,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('F',1,queen2);
        board.setFigure('D',1,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame3rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen2 = new Queen(false);
        Pawn pawn4 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('D',5,queen1);
        board.setFigure('E',2,pawn2);
        board.setFigure('F',7,pawn3);
        board.setFigure('B',3,queen2);
        board.setFigure('C',2,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame4rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('G',2,pawn1);
        board.setFigure('E',2,queen1);
        board.setFigure('A',4,pawn2);
        board.setFigure('F',3,pawn3);
        board.setFigure('D',3,queen2);
        board.setFigure('H',7,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame5rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(false);
        Pawn pawn11 = new Pawn(false);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(false);
        Queen queen4 = new Queen(true);
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('B',3,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('D',5,pawn4);
        board.setFigure('E',2,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',5,queen4);
        board.setFigure('G',8,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',3,pawn9);
        board.setFigure('C',6,pawn10);
        board.setFigure('D',7,pawn11);
        board.setFigure('F',5,pawn7);
        board.setFigure('H',3,pawn8);
        board.setFigure('G',2,queen2);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame6rev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(true);
        Pawn pawn11 = new Pawn(true);
        Pawn pawn12 = new Pawn(true);
        Pawn pawn13 = new Pawn(true);
        Pawn pawn14 = new Pawn(true);
        Pawn pawn15 = new Pawn(true);
        Pawn pawn16 = new Pawn(true);
        Pawn pawn17 = new Pawn(true);
        Pawn pawn18 = new Pawn(true);
        Pawn pawn19 = new Pawn(true);
        Pawn pawn20 = new Pawn(true);
        boolean result1,result2;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('D',1,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('D',3,pawn4);
        board.setFigure('E',4,pawn5);
        board.setFigure('G',4,pawn6);
        board.setFigure('D',5,pawn7);
        board.setFigure('F',5,pawn8);
        board.setFigure('H',5,pawn9);
        board.setFigure('E',6,pawn10);
        board.setFigure('A',2,pawn11);
        board.setFigure('A',4,pawn12);
        board.setFigure('B',3,pawn13);
        board.setFigure('B',5,pawn14);
        board.setFigure('C',4,pawn15);
        board.setFigure('F',1,pawn16);
        board.setFigure('F',3,pawn17);
        board.setFigure('G',2,pawn18);
        board.setFigure('H',1,pawn19);
        board.setFigure('H',3,pawn20);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoMovesBlackrev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen5 = new Queen(true);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(false);
        Queen queen4 = new Queen(false);
        boolean result;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('H',1,queen5);
        board.setFigure('H',5,queen4);
        board.setFigure('G',2,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',7,pawn2);
        board.setFigure('C',6,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('H',3,pawn6);
        board.setFigure('G',2,queen2);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.isDraw());
        Assert.assertTrue(VictoryValidator.getWinner());
    }

    @Test
    public void testNoMovesWhiterev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(true);
        Queen queen4 = new Queen(true);
        Queen queen5 = new Queen(true);
        Queen queen6 = new Queen(true);
        boolean result;
        //When
        board.setFigure('H',1,pawn1);
        board.setFigure('H',7,pawn2);
        board.setFigure('G',2,pawn3);
        board.setFigure('G',4,pawn4);
        board.setFigure('F',1,pawn5);
        board.setFigure('F',3,pawn6);
        board.setFigure('H',3,queen1);
        board.setFigure('H',5,queen2);
        board.setFigure('G',6,queen3);
        board.setFigure('G',8,queen4);
        board.setFigure('F',5,queen5);
        board.setFigure('F',7,queen6);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.isDraw());
        Assert.assertFalse(VictoryValidator.getWinner());
    }

    @Test
    public void testQueens15MovesRev(){
        //Given
        Board board = new Board();
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(false);
        boolean result;
        //When
        board.setFigure('A',2,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,16,15,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(VictoryValidator.isDraw());
    }

    @Test
    public void testNoFiguresBlackRev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(true);
        boolean result;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('B',5,pawn2);
        board.setFigure('D',3,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('A',4,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.getWinner());
    }

    @Test
    public void testNoFiguresWhiteRev(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        boolean result;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('B',5,pawn2);
        board.setFigure('D',3,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('A',4,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(true, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(VictoryValidator.getWinner());
    }

    /*******************
     * STANDARD RULES *
     ******************/

    @Test
    public void testNoEndOfGame1std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Queen queen4 = new Queen(true);
        Queen queen1 = new Queen(true);
        Queen queen3 = new Queen(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(false);
        Pawn pawn11 = new Pawn(false);
        Pawn pawn12 = new Pawn(false);
        Pawn pawn13 = new Pawn(false);
        Pawn pawn14 = new Pawn(false);
        Queen queen2 = new Queen(true);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('B',3,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('D',5,pawn4);
        board.setFigure('E',4,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',5,queen4);
        board.setFigure('G',8,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',1,pawn9);
        board.setFigure('C',6,pawn10);
        board.setFigure('D',7,pawn11);
        board.setFigure('E',2,pawn12);
        board.setFigure('F',5,pawn13);
        board.setFigure('H',1,pawn14);
        board.setFigure('A',2,queen2);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame2std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(false);
        Queen queen2 = new Queen(true);
        Pawn pawn4 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('A',2,queen1);
        board.setFigure('C',6,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('F',1,queen2);
        board.setFigure('D',1,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame3std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Queen queen2 = new Queen(false);
        Pawn pawn4 = new Pawn(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('A',2,pawn1);
        board.setFigure('D',5,queen1);
        board.setFigure('E',2,pawn2);
        board.setFigure('F',7,pawn3);
        board.setFigure('B',3,queen2);
        board.setFigure('C',2,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame4std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        boolean result1,result2;
        //When
        board.setFigure('G',2,pawn1);
        board.setFigure('E',2,queen1);
        board.setFigure('A',4,pawn2);
        board.setFigure('F',3,pawn3);
        board.setFigure('D',3,queen2);
        board.setFigure('H',7,pawn4);
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame5std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(false);
        Pawn pawn11 = new Pawn(false);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(false);
        Queen queen4 = new Queen(true);
        boolean result1,result2;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('B',3,pawn2);
        board.setFigure('C',4,pawn3);
        board.setFigure('D',5,pawn4);
        board.setFigure('E',2,pawn5);
        board.setFigure('F',7,pawn6);
        board.setFigure('H',5,queen4);
        board.setFigure('G',8,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',3,pawn9);
        board.setFigure('C',6,pawn10);
        board.setFigure('D',7,pawn11);
        board.setFigure('F',5,pawn7);
        board.setFigure('H',3,pawn8);
        board.setFigure('G',2,queen2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoEndOfGame6std(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Pawn pawn7 = new Pawn(false);
        Pawn pawn8 = new Pawn(false);
        Pawn pawn9 = new Pawn(false);
        Pawn pawn10 = new Pawn(true);
        Pawn pawn11 = new Pawn(true);
        Pawn pawn12 = new Pawn(true);
        Pawn pawn13 = new Pawn(true);
        Pawn pawn14 = new Pawn(true);
        Pawn pawn15 = new Pawn(true);
        Pawn pawn16 = new Pawn(true);
        Pawn pawn17 = new Pawn(true);
        Pawn pawn18 = new Pawn(true);
        Pawn pawn19 = new Pawn(true);
        Pawn pawn20 = new Pawn(true);
        boolean result1,result2;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('D',1,pawn2);
        board.setFigure('C',2,pawn3);
        board.setFigure('D',3,pawn4);
        board.setFigure('E',4,pawn5);
        board.setFigure('G',4,pawn6);
        board.setFigure('D',5,pawn7);
        board.setFigure('F',5,pawn8);
        board.setFigure('H',5,pawn9);
        board.setFigure('E',6,pawn10);
        board.setFigure('A',2,pawn11);
        board.setFigure('A',4,pawn12);
        board.setFigure('B',3,pawn13);
        board.setFigure('B',5,pawn14);
        board.setFigure('C',4,pawn15);
        board.setFigure('F',1,pawn16);
        board.setFigure('F',3,pawn17);
        board.setFigure('G',2,pawn18);
        board.setFigure('H',1,pawn19);
        board.setFigure('H',3,pawn20);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result1 = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        result2 = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
        Assert.assertFalse(VictoryValidator.isDraw());
    }

    @Test
    public void testNoMovesBlackstd(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Queen queen5 = new Queen(true);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Pawn pawn5 = new Pawn(false);
        Pawn pawn6 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(false);
        Queen queen4 = new Queen(false);
        boolean result;
        //When
        board.setFigure('A',8,pawn1);
        board.setFigure('H',1,queen5);
        board.setFigure('H',5,queen4);
        board.setFigure('G',2,queen1);
        board.setFigure('A',4,queen3);
        board.setFigure('B',7,pawn2);
        board.setFigure('C',6,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('F',3,pawn5);
        board.setFigure('H',3,pawn6);
        board.setFigure('G',2,queen2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.isDraw());
        Assert.assertFalse(VictoryValidator.getWinner());
    }

    @Test
    public void testNoMovesWhitestd(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Pawn pawn5 = new Pawn(true);
        Pawn pawn6 = new Pawn(true);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        Queen queen3 = new Queen(true);
        Queen queen4 = new Queen(true);
        Queen queen5 = new Queen(true);
        Queen queen6 = new Queen(true);
        boolean result;
        //When
        board.setFigure('H',1,pawn1);
        board.setFigure('H',7,pawn2);
        board.setFigure('G',2,pawn3);
        board.setFigure('G',4,pawn4);
        board.setFigure('F',1,pawn5);
        board.setFigure('F',3,pawn6);
        board.setFigure('H',3,queen1);
        board.setFigure('H',5,queen2);
        board.setFigure('G',6,queen3);
        board.setFigure('G',8,queen4);
        board.setFigure('F',5,queen5);
        board.setFigure('F',7,queen6);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.isDraw());
        Assert.assertTrue(VictoryValidator.getWinner());
    }

    @Test
    public void testQueens15Movesstd(){
        //Given
        Board board = new Board();
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(false);
        boolean result;
        //When
        board.setFigure('A',2,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,16,15,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(VictoryValidator.isDraw());
    }

    @Test
    public void testNoFiguresBlackstd(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(true);
        Pawn pawn2 = new Pawn(true);
        Pawn pawn3 = new Pawn(true);
        Pawn pawn4 = new Pawn(true);
        Queen queen1 = new Queen(true);
        Queen queen2 = new Queen(true);
        boolean result;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('B',5,pawn2);
        board.setFigure('D',3,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('A',4,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,false, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertTrue(VictoryValidator.getWinner());
    }

    @Test
    public void testNoFiguresWhitestd(){
        //Given
        Board board = new Board();
        Pawn pawn1 = new Pawn(false);
        Pawn pawn2 = new Pawn(false);
        Pawn pawn3 = new Pawn(false);
        Pawn pawn4 = new Pawn(false);
        Queen queen1 = new Queen(false);
        Queen queen2 = new Queen(false);
        boolean result;
        //When
        board.setFigure('B',1,pawn1);
        board.setFigure('B',5,pawn2);
        board.setFigure('D',3,pawn3);
        board.setFigure('D',7,pawn4);
        board.setFigure('A',4,queen1);
        board.setFigure('H',7,queen2);
        RulesSet ruleSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        result = VictoryValidator.validateEndOfGame(board,0,0,true, ruleSet);
        //Then
        Assert.assertTrue(result);
        Assert.assertFalse(VictoryValidator.getWinner());
    }


}
