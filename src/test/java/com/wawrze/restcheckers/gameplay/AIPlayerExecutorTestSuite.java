package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.aiplayer.AIPlayer;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.aiplayer.AIPlayerFactory;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.None;
import com.wawrze.restcheckers.domain.figures.Pawn;
import com.wawrze.restcheckers.domain.figures.Queen;
import com.wawrze.restcheckers.gameplay.moves.AIPlayerExecutor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AIPlayerExecutorTestSuite {

    private static int counter = 1;

    @Autowired
    private GameExecutor gameExecutor;

    @Autowired
    private AIPlayerExecutor aiPlayerExecutor;

    @Autowired
    private AIPlayerFactory aiPlayerFactory;

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
            aiPlayer = aiPlayerFactory.newAIPlayer(
                    board,
                    true,
                    rulesSet,
                    14,
                    14
            );
            aiPlayerExecutor.getAIMove(aiPlayer);
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
        boolean result = false;
        //When
        for(int i=0;i < 1000;i++)
            game.getInQueue().offer("next");
        try {
            gameExecutor.play(game);
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
        boolean result = false;
        //When
        for(int i=0;i < 200;i++)
            game.getInQueue().push("next");
        try {
            gameExecutor.play(game);
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
        boolean result = false;
        //When
        for(int i=0;i < 200;i++)
            game.getInQueue().push("next");
        for(int i = 1;i < 9;i++) {
            for(int j = 1;j < 9;j++)
                game.getBoard().setFigure((char) (i + 64), j, new None(true));
        }
        game.getBoard().setFigure('A', 4, new Pawn(true));
        game.getBoard().setFigure('H', 3, new Pawn(false));
        try {
            gameExecutor.play(game);
        }
        catch(Exception e) {
            result = true;
        }
        //Then
        Assert.assertFalse(result);
    }

}