package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.figures.None;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.userInterface.RestUI;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.junit.*;

public class GameTestSuite {

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("InGameUI tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("InGameUI tests: finished");
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
    public void testNullOrIncorrectMove() {
        //Given
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        RestUI restUI;
        boolean result = false;
        //When
        restUI = new RestUI();
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        restUI = new RestUI();
        restUI.getInQueue().offer("A1-Q9");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        restUI = new RestUI();
        restUI.getInQueue().offer("Abc");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        restUI = new RestUI();
        restUI.getInQueue().offer("A1-A2");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testQueenMovesCounter() {
        //Given
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        RestUI restUI = new RestUI();
        int blackQueenMovesCounter;
        int whiteQueenMovesCounter;
        boolean result = false;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 8, new Queen(true));
        game.getBoard().setFigure('H', 1, new Queen(false));
        restUI.getInQueue().offer("H1-G22");
        restUI.getInQueue().offer("H1-G2");
        restUI.getInQueue().offer("A8-B7");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        blackQueenMovesCounter = game.getBlackQueenMoves();
        whiteQueenMovesCounter = game.getWhiteQueenMoves();
        //Then
        Assert.assertEquals(1, whiteQueenMovesCounter);
        Assert.assertEquals(1, blackQueenMovesCounter);
        Assert.assertFalse(result);
    }

    @Test
    public void testPawnToQueen() {
        //Given
        RulesSet rulesSet = new RulesSet(true, true, true,
                true, true, true,
                "", "");
        Game game = new Game("", rulesSet, false, false);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 7, new Pawn(false));
        game.getBoard().setFigure('G', 2, new Pawn(true));
        restUI.getInQueue().offer("B7-A8");
        restUI.getInQueue().offer("G2-H1");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertTrue(game.getBoard().getFigure('A', 8) instanceof Queen);
        Assert.assertTrue(game.getBoard().getFigure('H', 1) instanceof Queen);
        Assert.assertFalse(result);
    }

    @Test
    public void testMultiCapture() {
        //Given
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, false, true);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 3, new Pawn(true));
        game.getBoard().setFigure('D', 3, new Pawn(true));
        game.getBoard().setFigure('E', 8, new Pawn(true));
        game.getBoard().setFigure('F', 5, new Pawn(true));
        game.getBoard().setFigure('B', 7, new Pawn(false));
        game.getBoard().setFigure('D', 7, new Pawn(false));
        game.getBoard().setFigure('G', 6, new Pawn(false));
        restUI.getInQueue().offer("e8-c6");
        restUI.getInQueue().offer("c6-d5");
        restUI.getInQueue().offer("c6-a8");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testMultiCapture2() {
        //Given
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        Game game = new Game("", rulesSet, true, false);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('B', 3, new Pawn(true));
        game.getBoard().setFigure('B', 5, new Pawn(true));
        game.getBoard().setFigure('D', 7, new Pawn(true));
        game.getBoard().setFigure('F', 7, new Pawn(true));
        game.getBoard().setFigure('C', 2, new Pawn(false));
        game.getBoard().setFigure('E', 2, new Pawn(false));
        game.getBoard().setFigure('G', 2, new Pawn(false));
        game.getBoard().setFigure('G', 6, new Pawn(false));
        restUI.getInQueue().offer("g6-e8");
        restUI.getInQueue().offer("e8-c6");
        restUI.getInQueue().offer("c6-b7");
        restUI.getInQueue().offer("c6-a4");
        restUI.getInQueue().offer("x");
        try {
            game.play(restUI);
        }
        catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

}