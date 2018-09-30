package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.None;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.userInterface.RestUI;
import org.junit.*;

public class AIPlayerTestSuite {

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
    public void testEndOfGameDrawEvaluation() {
        //Given
        Board board = new Board.BoardBuilder()
                .addFigure('A', 8, new Queen(true))
                .addFigure('H', 7, new Queen(false))
                .build();
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        AIPlayer aiPlayer;
        boolean result = false;
        //When
        try {
            aiPlayer = new AIPlayer(board, true, rulesSet, 14, 14);
            aiPlayer.getAIMove();
        } catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testGameAIvsAIstandardVictoryConditions() {
        //Given
        RulesSet rulesSet = new RulesSet(false, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i=0;i < 1000;i++)
            restUI.getInQueue().offer("next");
        try {
            game.play(restUI);
        }
        catch (Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testGameAIvsAIreversedVictoryConditions() {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i=0;i < 200;i++)
            restUI.getInQueue().push("next");
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
    public void testEqualEvaluation() {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        boolean result = false;
        //When
        for(int i=0;i < 200;i++)
            restUI.getInQueue().push("next");
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 4, new Pawn(true));
        game.getBoard().setFigure('H', 3, new Pawn(false));
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