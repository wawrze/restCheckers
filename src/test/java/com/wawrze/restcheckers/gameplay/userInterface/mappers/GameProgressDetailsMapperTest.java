package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSets;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameProgressDetailsDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameProgressDetailsMapperTest {

    @Autowired
    GameProgressDetailsMapper gameProgressDetailsMapper;

    @Autowired
    RulesSets rulesSets;

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
    public void testGameProgressDetailsMapper() {
        //Given
        Game game = new Game(
                "some name",
                rulesSets.getRules().get(0),
                true,
                true
        );
        GameProgressDetailsDto gameProgressDetailsDto = new GameProgressDetailsDto();
        //When
        gameProgressDetailsDto = gameProgressDetailsMapper.mapToGameProgressDetailsDto(game);
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

}