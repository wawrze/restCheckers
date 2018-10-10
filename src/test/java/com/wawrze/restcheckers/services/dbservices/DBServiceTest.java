package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBServiceTest {

    @Autowired
    DBService dbService;

    private static int counter = 1;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("DBService tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("DBService tests: finished");
    }

    @Before
    public void before() {
        System.out.println("Test #" + counter + ": started");
    }

    @After
    public void after() {
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }

    /////// RULES ////////////////////////////////////////////////

    @Test
    public void shouldSaveRulesSet() {
        //Given
        RulesSet rulesSet = new RulesSet();
        //When
        dbService.saveRulesSet(rulesSet);
        Long id = rulesSet.getId();
        //Then
        assertNotEquals(0, id, 0);
        //Clean up
        dbService.deleteRulesSet(id);
    }

    @Test
    public void shouldNotSaveRulesSet() {
        //Given
        RulesSet rulesSet1 = new RulesSet(
                true,
                true,
                true,
                true,
                true,
                true,
                "test",
                ""
        );
        RulesSet rulesSet2 = new RulesSet(
                true,
                true,
                true,
                true,
                true,
                true,
                "test",
                ""
        );
        //When
        dbService.saveRulesSet(rulesSet1);
        Long id = rulesSet1.getId();
        dbService.saveRulesSet(rulesSet2);
        //Then
        assertNull(rulesSet2.getId());
        //Clean up
        dbService.deleteRulesSet(id);
    }

    @Test
    public void shouldGetAllRulesSets() {
        //Given
        RulesSet rulesSet1 = new RulesSet(
                true,
                true,
                true,
                true,
                true,
                true,
                "test1",
                ""
        );
        RulesSet rulesSet2 = new RulesSet(
                true,
                true,
                true,
                true,
                true,
                true,
                "test2",
                ""
        );
        //When
        dbService.saveRulesSet(rulesSet1);
        Long id1 = rulesSet1.getId();
        dbService.saveRulesSet(rulesSet2);
        Long id2 = rulesSet2.getId();
        List<RulesSet> resultList = dbService.getAllRulesSets();
        //Then
        assertEquals(5, resultList.size());
        //Clean up
        dbService.deleteRulesSet(id1);
        dbService.deleteRulesSet(id2);
    }

    @Test
    public void shouldGetRulesSet() {
        //Given
        RulesSet rulesSet = new RulesSet(
                true,
                true,
                true,
                true,
                true,
                true,
                "test",
                ""
        );
        //When
        dbService.saveRulesSet(rulesSet);
        Long id = rulesSet.getId();
        RulesSet resultRulesSet = dbService.getRulesSetByName(rulesSet.getName());
        //Then
        assertEquals(rulesSet.getId(), resultRulesSet.getId());
        assertEquals(rulesSet.getName(), resultRulesSet.getName());
        assertEquals(rulesSet.getDescription(), resultRulesSet.getDescription());
        assertEquals(rulesSet.isVictoryConditionsReversed(), resultRulesSet.isVictoryConditionsReversed());
        //Clean up
        dbService.deleteRulesSet(id);
    }

    @Test
    public void shouldNotGetRulesSet() {
        //Given
        //When
        RulesSet resultRulesSet = dbService.getRulesSetByName("wrong name");
        //Then
        assertNull(resultRulesSet);
        //Clean up
    }

    /////// GAME /////////////////////////////////////////////////

    @Test
    public void shouldSaveGame() {
        //Given
        Game game = new Game();
        //When
        dbService.saveGame(game);
        Long id = game.getId();
        //Then
        assertNotEquals(0, id, 0);
        //Clean up
        dbService.deleteGame(id);
    }

    @Test
    public void shouldGetGamesList() {
        //Given
        Game game1 = new Game();
        Game game2 = new Game();
        //When
        dbService.saveGame(game1);
        Long id1 = game1.getId();
        dbService.saveGame(game2);
        Long id2 = game2.getId();
        List<Game> resultList = dbService.getAllGames();
        //Then
        assertTrue(resultList.size() >= 2);
        //Clean up
        dbService.deleteGame(id1);
        dbService.deleteGame(id2);
    }

    /////// FINISHED GAME ////////////////////////////////////////

    @Test
    public void shouldSaveFinishedGame() {
        //Given
        String gameName = "test name";
        Game game = new Game(gameName, null, false, false);
        //When
        dbService.saveFinishedGame(game);
        List<FinishedGame> finishedGames = dbService.getFinishedGames();
        FinishedGame finishedGame = finishedGames.get(finishedGames.size() - 1);
        Long id = finishedGame.getId();
        //Then
        assertEquals(gameName, finishedGame.getName());
        //Clean up
        dbService.deleteFinishedGame(id);
    }

}