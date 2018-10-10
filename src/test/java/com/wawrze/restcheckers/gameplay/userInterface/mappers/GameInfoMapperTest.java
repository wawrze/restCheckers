package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSets;
import com.wawrze.restcheckers.dtos.GameInfoDto;
import com.wawrze.restcheckers.dtos.mappers.BoardMapper;
import com.wawrze.restcheckers.dtos.mappers.GameInfoMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameInfoMapperTest {

    @Autowired
    GameInfoMapper gameInfoMapper;

    @Autowired
    RulesSets rulesSets;

    @Autowired
    BoardMapper boardMapper;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("GameProgressDetailsMapper tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("GameProgressDetailsMapper tests: finished");
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
    public void testGameInfoMapper() {
        //Given
        Game game = new Game(
                "some name",
                rulesSets.updateRules().get(0),
                true,
                true
        );
        GameInfoDto gameInfoDto = new GameInfoDto();
        //When
        game.updateLastAction();
        gameInfoDto = gameInfoMapper.mapToGameProgressDetailsDto(game);
        //Then
        assertEquals(game.getName(), gameInfoDto.getName());
        assertEquals(game.getRulesSet().getName(), gameInfoDto.getRulesName());
        assertEquals("Game started.", gameInfoDto.getGameStatus());
        assertTrue(gameInfoDto.getMovesHistory().isEmpty());
        assertEquals(game.isActivePlayer(), gameInfoDto.isActivePlayer());
        assertEquals(game.isWhiteAIPlayer(), gameInfoDto.isWhiteAIPlayer());
        assertEquals(game.isBlackAIPlayer(), gameInfoDto.isBlackAIPlayer());
        assertEquals(game.isFinished(), gameInfoDto.isFinished());
        assertEquals(game.isDraw(), gameInfoDto.isDraw());
        assertEquals(game.isWinner(), gameInfoDto.isWinner());
        assertTrue(gameInfoDto.getTypeOfWin().isEmpty());
        assertEquals(0, gameInfoDto.getMoves());
        assertEquals(0, gameInfoDto.getBlackQueenMoves());
        assertEquals(0, gameInfoDto.getWhiteQueenMoves());
        assertEquals(12, gameInfoDto.getBlackPawns());
        assertEquals(12, gameInfoDto.getWhitePawns());
        assertEquals(0, gameInfoDto.getBlackQueens());
        assertEquals(0, gameInfoDto.getWhiteQueens());
        assertEquals(game.getStartTime().getYear(), gameInfoDto.getStartTime().getYear());
        assertEquals(game.getStartTime().getMonthValue(), gameInfoDto.getStartTime().getMonth());
        assertEquals(game.getStartTime().getDayOfMonth(), gameInfoDto.getStartTime().getDay());
        assertEquals(game.getStartTime().getHour(), gameInfoDto.getStartTime().getHour());
        assertEquals(game.getStartTime().getMinute(), gameInfoDto.getStartTime().getMinute());
        assertEquals(game.getStartTime().getSecond(), gameInfoDto.getStartTime().getSecond());
        assertEquals(game.getLastAction().getYear(), gameInfoDto.getLastAction().getYear());
        assertEquals(game.getLastAction().getMonthValue(), gameInfoDto.getLastAction().getMonth());
        assertEquals(game.getLastAction().getDayOfMonth(), gameInfoDto.getLastAction().getDay());
        assertEquals(game.getLastAction().getHour(), gameInfoDto.getLastAction().getHour());
        assertEquals(game.getLastAction().getMinute(), gameInfoDto.getLastAction().getMinute());
        assertEquals(game.getLastAction().getSecond(), gameInfoDto.getLastAction().getSecond());

    }

}