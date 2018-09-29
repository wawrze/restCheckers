package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.None;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.userInterface.RestUI;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
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
    public void testEndOfGameDrawEvaluation() throws IncorrectMoveFormat, IncorrectMoveException {
        //Given
        Board board = new Board();
        RulesSet rulesSet = new RulesSet(false, false, false,
                false, true, false,
                "", "");
        AIPlayer aiPlayer;
        //When
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                board.setFigure((char) (i + 64), j, new None(true));
        }
        board.setFigure('A', 8, new Queen(true));
        board.setFigure('H', 7, new Queen(false));
        aiPlayer = new AIPlayer(board, true, rulesSet, 14, 14);
        aiPlayer.getAIMove();
        //Then
    }

    @Test
    public void testGameAIvsAIstandardVictoryConditions() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(false, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        //When
        for(int i=0;i < 200;i++)
            restUI.getInQueue().push("next");
        game.play(restUI);
        //Then
    }

    @Test
    public void testGameAIvsAIreversedVictoryConditions() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        //When
        for(int i=0;i < 200;i++)
            restUI.getInQueue().push("next");
        game.play(restUI);
        //Then
    }

    @Test
    public void testEqualEvaluation() throws IncorrectMoveException, IncorrectMoveFormat {
        //Given
        RulesSet rulesSet = new RulesSet(true, false, false,
                false,true, false,
                "", "");
        Game game = new Game("", rulesSet, true, true);
        RestUI restUI = new RestUI();
        //When
        for(int i=0;i < 200;i++)
            restUI.getInQueue().push("next");
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 4, new Pawn(true));
        game.getBoard().setFigure('H', 3, new Pawn(false));
        game.play(restUI);
        //Then
    }

}