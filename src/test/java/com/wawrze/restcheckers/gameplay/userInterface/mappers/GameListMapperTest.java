package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSets;
import com.wawrze.restcheckers.dtos.GameListDto;
import com.wawrze.restcheckers.dtos.mappers.GameListMapper;
import com.wawrze.restcheckers.gameplay.GameEnvelope;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameListMapperTest {

    private static int counter = 1;
    @Autowired
    GameEnvelope gameEnvelope;
    @Autowired
    GameListMapper gameListMapper;
    @Autowired
    RulesSets rulesSets;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("GameListMapper tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("GameListMapper tests: finished");
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
    public void testMapToGameListDto() {
        //Given
        gameEnvelope.getGames().put(1L, new Game(
                "game1",
                rulesSets.updateRules().get(0),
                false,
                false
        ));
        gameEnvelope.getGames().put(2L, new Game(
                "game2",
                rulesSets.updateRules().get(1),
                true,
                true
        ));
        gameEnvelope.getGames().put(3L, new Game(
                "game3",
                rulesSets.updateRules().get(2),
                false,
                true
        ));
        GameListDto gameListDto;
        //When
        gameEnvelope.getGames().values()
                .forEach(Game::updateLastAction);
        gameListDto = gameListMapper.mapToGameListDto();
        //Then
        assertEquals(3, gameListDto.getGamesList().size());
    }

}