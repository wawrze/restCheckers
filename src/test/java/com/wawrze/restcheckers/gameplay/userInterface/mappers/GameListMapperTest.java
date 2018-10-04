package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSets;
import com.wawrze.restcheckers.gameplay.userInterface.GameEnvelope;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameListDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameListMapperTest {

    @Autowired
    GameEnvelope gameEnvelope;

    @Autowired
    GameListMapper gameListMapper;

    @Autowired
    RulesSets rulesSets;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("GameListMapper tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("GameListMapper tests: finished");
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
    public void testMapToGameListDto() {
        //Given
        gameEnvelope.getGames().put("game1", new Game(
                "game1",
                rulesSets.updateRules().get(0),
                false,
                false
        ));
        gameEnvelope.getGames().put("game2", new Game(
                "game2",
                rulesSets.updateRules().get(1),
                true,
                true
        ));
        gameEnvelope.getGames().put("game3", new Game(
                "game3",
                rulesSets.updateRules().get(2),
                false,
                true
        ));
        GameListDto gameListDto = new GameListDto();
        //When
        gameListDto = gameListMapper.mapToGameListDto();
        //Then
        assertEquals(3, gameListDto.getGamesList().size());
    }

}