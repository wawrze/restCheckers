package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSets;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import exceptions.httpExceptions.ForbiddenException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameEnvelopeTest {

    @Autowired
    GameEnvelope gameEnvelope;

    @Autowired
    RulesSets rulesSets;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("GameEnvelope tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("GameEnvelope tests: finished");
    }

    @Before
    public void before(){
        System.out.println("Test #" + counter + ": started");
        gameEnvelope.getGames().clear();
    }

    @After
    public void after(){
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    @Test
    public void shouldStartNewGame() {
        //Given
        GameDto gameDto = new GameDto(
                "some name",
                "classic",
                "false",
                "false"
        );
        RestUI restUI = new RestUI();
        restUI.getInQueue().offer("x");
        gameEnvelope.getRestUIs().put("some name", restUI);
        //When
        gameEnvelope.startNewGame(gameDto);
        //Then
        assertEquals(1, gameEnvelope.getGames().size());
    }

    @Test
    public void shouldNotStartNewGame() {
        //Given
        GameDto gameDto = new GameDto(
                "test1",
                "classic",
                "false",
                "false"
        );
        RestUI restUI = new RestUI();
        restUI.getInQueue().offer("x");
        gameEnvelope.getRestUIs().put("test1", restUI);
        gameEnvelope.getGames().put("test1", new Game(
                "test1",
                rulesSets.getRules().get(0),
                true,
                true
        ));
        boolean result = false;
        //When
        try {
            gameEnvelope.startNewGame(gameDto);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldServeNewMove() {
        //Given
        MoveDto moveDto = new MoveDto("F1-E2");
        String gameName = "game name";
        Game game = new Game(
                gameName,
                rulesSets.getRules().get(0),
                false,
                false
        );
        gameEnvelope.getGames().put(gameName, game);
        gameEnvelope.getRestUIs().put(gameName, new RestUI());
        //When
        BoardDto boardDto = gameEnvelope.sendMove(gameName, moveDto);
        //Then
        assertEquals("Game started.", boardDto.getGameStatus());
    }

    @Test
    public void shouldNotServeNewMove() {
        //Given
        MoveDto moveDto = new MoveDto("F1-E2");
        String gameName = "game name";
        boolean result = false;
        //When
        try {
            gameEnvelope.sendMove(gameName, moveDto);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetBoard() {
        //Given
        String gameName = "game name";
        Game game = new Game(
                gameName,
                rulesSets.getRules().get(0),
                false,
                false
        );
        gameEnvelope.getGames().put(gameName, game);
        gameEnvelope.getRestUIs().put(gameName, new RestUI());
        //When
        BoardDto boardDto = gameEnvelope.getBoard(gameName);
        //Then
        assertEquals("Game started.", boardDto.getGameStatus());
    }

    @Test
    public void shouldNotGetBoard() {
        //Given
        String gameName = "game name";
        boolean result = false;
        //When
        try {
            gameEnvelope.getBoard(gameName);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetRulesSets() {
        //Given
        //When
        RulesSetsDto rulesSetsDto = gameEnvelope.getRulesSets();
        //Then
        assertEquals(3, rulesSetsDto.getRules().size());
    }

    @Test
    public void shouldGetRulesSet() {
        //Given
        String rulesSetName = "classic";
        //When
        RulesSetDto rulesSetDto = gameEnvelope.getRulesSet(rulesSetName);
        //Then
        assertEquals(rulesSets.getRules().get(0).getName(), rulesSetDto.getName());
        assertEquals(rulesSets.getRules().get(0).getDescription(), rulesSetDto.getDescription());
    }

    @Test
    public void shouldNotGetRulesSet() {
        //Given
        String rulesSetName = "not existing name";
        boolean result = false;
        //When
        try {
            RulesSetDto rulesSetDto = gameEnvelope.getRulesSet(rulesSetName);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetGameProgressDetails() {
        //Given
        String gameName = "game name";
        Game game = new Game(
                gameName,
                rulesSets.getRules().get(0),
                false,
                false
        );
        gameEnvelope.getGames().put(gameName, game);
        gameEnvelope.getRestUIs().put(gameName, new RestUI());
        //When
        GameProgressDetailsDto gameProgressDetailsDto = gameEnvelope.getGameProgressDetails(gameName);
        //Then
        assertEquals(0, gameProgressDetailsDto.getMoves());
        assertEquals(0, gameProgressDetailsDto.getBlackQueenMoves());
        assertEquals(0, gameProgressDetailsDto.getWhiteQueenMoves());
        assertEquals(12, gameProgressDetailsDto.getBlackPawns());
        assertEquals(12, gameProgressDetailsDto.getWhitePawns());
        assertEquals(0, gameProgressDetailsDto.getBlackQueens());
        assertEquals(0, gameProgressDetailsDto.getWhiteQueens());
        assertFalse(gameProgressDetailsDto.isFinished());
        assertFalse(gameProgressDetailsDto.isDraw());
        assertFalse(gameProgressDetailsDto.isWinner());
    }

    @Test
    public void shouldNotGetGameProgressDetails() {
        //Given
        String gameName = "no such game name";
        boolean result = false;
        //When
        try {
            gameEnvelope.getGameProgressDetails(gameName);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetFinishedGameProgressDetails() {
        //Given
        String gameName = "test name";
        GameDto gameDto = new GameDto(
                gameName,
                "classic",
                "true",
                "true"
        );
        RestUI restUI = new RestUI();
        IntStream.iterate(0, i -> ++i)
                .limit(1000)
                .forEach(i -> restUI.getInQueue().offer("next"));
        gameEnvelope.getRestUIs().put(gameName, restUI);
        //When
        gameEnvelope.startNewGame(gameDto);
        GameProgressDetailsDto gameProgressDetailsDto = gameEnvelope.getGameProgressDetails(gameName);
        //Then
        assertTrue(gameProgressDetailsDto.isFinished());
        assertNotEquals(0, gameProgressDetailsDto.getMoves());
    }

    @Test
    public void shouldDeleteGame() {
        //Given
        String gameName = "game to delete";
        Game game = new Game(
                gameName,
                rulesSets.getRules().get(0),
                false,
                false
        );
        gameEnvelope.getGames().put(gameName, game);
        gameEnvelope.getRestUIs().put(gameName, new RestUI());
        //When
        gameEnvelope.deleteGame(gameName);
        //Then
        assertFalse(gameEnvelope.getGames().containsKey(gameName));
    }

    @Test
    public void shouldNotDeleteGame() {
        //Given
        String gameName = "game to delete";
        boolean result = false;
        //When
        try {
            gameEnvelope.deleteGame(gameName);
        }
        catch(ForbiddenException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetGameList() {
        //Given
        gameEnvelope.getGames().put("game1", new Game(
                "game1",
                rulesSets.getRules().get(0),
                false,
                false
        ));
        gameEnvelope.getGames().put("game2", new Game(
                "game2",
                rulesSets.getRules().get(1),
                true,
                true
        ));
        gameEnvelope.getGames().put("game3", new Game(
                "game3",
                rulesSets.getRules().get(2),
                false,
                true
        ));
        //When
        GameListDto gameListDto = gameEnvelope.getGameList();
        //Then
        assertEquals(3, gameListDto.getGamesList().size());
    }

}