package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.RulesSets;
import com.wawrze.restcheckers.dtos.*;
import com.wawrze.restcheckers.gameplay.GameEnvelope;
import com.wawrze.restcheckers.services.dbservices.DBService;
import exceptions.MethodFailureException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameEnvelopeTest {

    private static int counter = 1;
    @Autowired
    GameEnvelope gameEnvelope;
    @Autowired
    RulesSets rulesSets;
    @Autowired
    DBService dbService;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("GameEnvelope tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("GameEnvelope tests: finished");
    }

    @Before
    public void before() {
        System.out.println("Test #" + counter + ": started");
        gameEnvelope.getGames().clear();
    }

    @After
    public void after() {
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
        //When
        Long id = gameEnvelope.startNewGame(gameDto);
        //Then
        assertEquals(1, gameEnvelope.getGames().size());
        //Clean up
        dbService.deleteGame(id);
    }

    @Test
    public void shouldServeNewMove() {
        //Given
        MoveDto moveDto = new MoveDto("F1-E2");
        GameDto gameDto = new GameDto(
                "test",
                "classic",
                "false",
                "false"
        );
        Long id = gameEnvelope.startNewGame(gameDto);
        //When
        gameEnvelope.sendMove(id, moveDto);
        //Then
        assertEquals("Game started.", gameEnvelope.getGames().get(id).getGameStatus());
        //Clean up
        dbService.deleteGame(id);
    }

    @Test
    public void shouldNotServeNewMove() {
        //Given
        MoveDto moveDto = new MoveDto("F1-E2");
        boolean result = false;
        //When
        try {
            gameEnvelope.sendMove(0L, moveDto);
        } catch (MethodFailureException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetGameInfo() {
        //Given
        GameDto gameDto = new GameDto(
                "test",
                "classic",
                "false",
                "false"
        );
        //When
        Long id = gameEnvelope.startNewGame(gameDto);
        gameEnvelope.getGames().get(id).updateLastAction();
        GameInfoDto gameInfoDto = gameEnvelope.getGameInfo(id);
        //Then
        assertEquals("Game started.", gameInfoDto.getGameStatus());
        //Clean up
        dbService.deleteGame(id);
    }

    @Test
    public void shouldNotGetGameInfo() {
        //Given
        boolean result = false;
        //When
        try {
            gameEnvelope.getGameInfo(0L);
        } catch (MethodFailureException e) {
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
        //When
        RulesSetDto rulesSetDto = gameEnvelope.getRulesSet("classic");
        //Then
        assertEquals(rulesSets.updateRules().get(0).getName(), rulesSetDto.getName());
        assertEquals(rulesSets.updateRules().get(0).getDescription(), rulesSetDto.getDescription());
    }

    @Test
    public void shouldNotGetRulesSet() {
        //Given
        boolean result = false;
        //When
        try {
            //noinspection unused
            RulesSetDto rulesSetDto = gameEnvelope.getRulesSet("not existing name");
        } catch (MethodFailureException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetFinishedGameInfo() {
        //Given
        GameDto gameDto = new GameDto(
                "test",
                "classic",
                "true",
                "true"
        );
        //When
        Long id = gameEnvelope.startNewGame(gameDto);
        Game game = gameEnvelope.getGames().get(id);
        IntStream.iterate(0, i -> ++i)
                .limit(2000)
                .forEach(i -> game.getInQueue().offer("next"));
        gameEnvelope.playGame(id);
        GameInfoDto gameInfoDto = gameEnvelope.getGameInfo(id);
        //Then
        assertTrue(gameInfoDto.isFinished());
        assertNotEquals(0, gameInfoDto.getMoves());
        //Clean up
        dbService.deleteGame(id);
        List<FinishedGame> finishedGames = dbService.getFinishedGames();
        FinishedGame finishedGame = finishedGames.get(finishedGames.size() - 1);
        id = finishedGame.getId();
        dbService.deleteFinishedGame(id);
    }

    @Test
    public void shouldDeleteGame() {
        //Given
        GameDto gameDto = new GameDto(
                "test",
                "classic",
                "true",
                "true"
        );
        Long id = gameEnvelope.startNewGame(gameDto);
        //When
        gameEnvelope.deleteGame(id);
        //Then
        assertFalse(gameEnvelope.getGames().containsKey(id));
    }

    @Test
    public void shouldNotDeleteGame() {
        //Given
        boolean result = false;
        //When
        try {
            gameEnvelope.deleteGame(0L);
        } catch (MethodFailureException e) {
            result = true;
        }
        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetGameList() {
        //Given
        gameEnvelope.getGames().put(1L, new Game(
                "game1",
                new RulesSet(),
                false,
                false
        ));
        gameEnvelope.getGames().put(2L, new Game(
                "game2",
                new RulesSet(),
                true,
                true
        ));
        gameEnvelope.getGames().put(3L, new Game(
                "game3",
                new RulesSet(),
                false,
                true
        ));
        //When
        GameListDto gameListDto = gameEnvelope.getGameList();
        //Then
        assertEquals(3, gameListDto.getGamesList().size());
    }

}