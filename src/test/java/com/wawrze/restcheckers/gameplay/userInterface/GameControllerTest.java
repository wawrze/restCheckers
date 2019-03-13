package com.wawrze.restcheckers.gameplay.userInterface;

import com.google.gson.Gson;
import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.dtos.*;
import com.wawrze.restcheckers.dtos.mappers.BoardMapper;
import com.wawrze.restcheckers.gameplay.GameEnvelope;
import exceptions.MethodFailureException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GameControllerTest {

    private static int counter = 1;
    @InjectMocks
    private BoardMapper boardMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameEnvelope gameEnvelope;
    private Gson gson;

    @BeforeClass
    public static void beforeTests() {
        System.out.println("Controller tests: started");
    }

    @AfterClass
    public static void afterTests() {
        System.out.println("Controller tests: finished");
    }

    @Before
    public void before() {
        System.out.println("Test #" + counter + ": started");
        gson = new Gson();
    }

    @After
    public void after() {
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }


    @Test
    public void shouldGetGameInfo() throws Exception {
        //Given
        GameInfoDto gameInfoDto = new GameInfoDto(
                "name",
                "rules",
                "status",
                "1. white: F1-E2",
                boardMapper.mapToBoardDto(new Board.BoardBuilder().build().getNewBoard()),
                true,
                false,
                false,
                false,
                false,
                false,
                "no win",
                10,
                5,
                7,
                1,
                2,
                3,
                4,
                new DateTimeDto(2018, 1, 1, 12, 0, 0),
                new DateTimeDto(2020, 12, 31, 18, 5, 30)
        );
        when(gameEnvelope.getGameInfo(anyLong())).thenReturn(gameInfoDto);
        //When & Then
        mockMvc.perform(get("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.rulesName", is("rules")))
                .andExpect(jsonPath("$.activePlayer", is(true)))
                .andExpect(jsonPath("$.whiteAIPlayer", is(false)))
                .andExpect(jsonPath("$.blackAIPlayer", is(false)));
        verify(gameEnvelope, times(1)).getGameInfo(any());
    }

    @Test
    public void shouldNotGetGameInfo() throws Exception {
        //Given
        when(gameEnvelope.getGameInfo(anyLong())).thenThrow(new MethodFailureException(""));
        //When & Then
        mockMvc.perform(get("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).getGameInfo(any());
    }

    @Test
    public void shouldGetRulesSets() throws Exception {
        //Given
        RulesSetsDto rulesSetsDto = new RulesSetsDto();
        rulesSetsDto.getRules().add(new RulesSetDto());
        rulesSetsDto.getRules().add(new RulesSetDto());
        rulesSetsDto.getRules().add(new RulesSetDto());
        when(gameEnvelope.getRulesSets()).thenReturn(rulesSetsDto);
        //When & Then
        mockMvc.perform(get("/v1/rules")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.rules", hasSize(3)));
        verify(gameEnvelope, times(1)).getRulesSets();
    }

    @Test
    public void shouldGetRulesSet() throws Exception {
        //Given
        RulesSetDto rulesSetDto = new RulesSetDto(
                false,
                false,
                false,
                false,
                true,
                false,
                "classic",
                ""
        );
        when(gameEnvelope.getRulesSet("classic")).thenReturn(rulesSetDto);
        //When & Then
        mockMvc.perform(get("/v1/rules/classic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("classic")))
                .andExpect(jsonPath("$.captureAny", is(false)))
                .andExpect(jsonPath("$.victoryConditionsReversed", is(false)))
                .andExpect(jsonPath("$.pawnCaptureBackward", is(true)));
        verify(gameEnvelope, times(1)).getRulesSet(any());
    }

    @Test
    public void shouldNotGetRulesSet() throws Exception {
        //Given
        when(gameEnvelope.getRulesSet("classic")).thenThrow(new MethodFailureException(""));
        //When & Then
        mockMvc.perform(get("/v1/rules/classic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).getRulesSet(any());
    }

    @Test
    public void shouldGetGames() throws Exception {
        //When
        List<GameInfoDto> gameList = new ArrayList<>();
        gameList.add(new GameInfoDto(
                "some name",
                "classic",
                "status",
                "1. white: F1-E2",
                null,
                true,
                true,
                true,
                false,
                false,
                false,
                "no win",
                10,
                5,
                7,
                1,
                2,
                3,
                4,
                new DateTimeDto(2018, 1, 1, 12, 0, 0),
                new DateTimeDto(2020, 12, 31, 18, 5, 30)
        ));
        gameList.add(new GameInfoDto(
                "some other name",
                "english",
                "status",
                "1. white: F1-E2",
                null,
                true,
                true,
                true,
                false,
                false,
                false,
                "no win",
                10,
                5,
                7,
                1,
                2,
                3,
                4,
                new DateTimeDto(2018, 1, 1, 12, 0, 0),
                new DateTimeDto(2020, 12, 31, 18, 5, 30)
        ));
        gameList.add(new GameInfoDto());
        GameListDto gameListDto = new GameListDto(gameList);
        when(gameEnvelope.getGameList()).thenReturn(gameListDto);
        //When & Then
        mockMvc.perform(get("/v1/games")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gamesList", hasSize(3)))
                .andExpect(jsonPath("$.gamesList[0].name", is("some name")))
                .andExpect(jsonPath("$.gamesList[0].rulesName", is("classic")))
                .andExpect(jsonPath("$.gamesList[0].finished", is(false)))
                .andExpect(jsonPath("$.gamesList[0].draw", is(false)))
                .andExpect(jsonPath("$.gamesList[0].winner", is(false)))
                .andExpect(jsonPath("$.gamesList[1].name", is("some other name")))
                .andExpect(jsonPath("$.gamesList[1].rulesName", is("english")))
                .andExpect(jsonPath("$.gamesList[1].whiteAIPlayer", is(true)))
                .andExpect(jsonPath("$.gamesList[1].blackAIPlayer", is(true)));
        verify(gameEnvelope, times(1)).getGameList();
    }

    @Test
    public void shouldStartNewGame() throws Exception {
        //Given
        GameDto gameDto = new GameDto(
                "some name",
                "classic",
                "false",
                "false"
        );
        String jsonContent = gson.toJson(gameDto);
        //When & Then
        mockMvc.perform(post("/v1/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(gameEnvelope, times(1)).startNewGame(any());
    }

    @Test
    public void shouldNotStartNewGame1() throws Exception {
        //Given
        GameDto gameDto = new GameDto(
                "some name",
                "classic",
                "false",
                "false"
        );
        String jsonContent = gson.toJson(gameDto);
        doThrow(new MethodFailureException("")).when(gameEnvelope).startNewGame(any());
        //When & Then
        mockMvc.perform(post("/v1/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).startNewGame(any());
    }

    @Test
    public void shouldPlayGame() throws Exception {
        //Given
        doNothing().when(gameEnvelope).playGame(anyLong());
        //When & Then
        mockMvc.perform(post("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(gameEnvelope, times(1)).playGame(any());
    }

    @Test
    public void shouldNotPlayGame() throws Exception {
        //Given
        doThrow(new MethodFailureException("")).when(gameEnvelope).playGame(anyLong());
        //When & Then
        mockMvc.perform(post("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).playGame(any());
    }

    @Test
    public void shouldServeNewMove() throws Exception {
        //Given
        MoveDto moveDto = new MoveDto("A1-B2");
        String jsonContent = gson.toJson(moveDto);
        //When & Then
        mockMvc.perform(put("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
        verify(gameEnvelope, times(1)).sendMove(anyLong(), any());
    }

    @Test
    public void shouldNotServeNewMove() throws Exception {
        //Given
        MoveDto moveDto = new MoveDto("A1-B2");
        String jsonContent = gson.toJson(moveDto);
        doThrow(new MethodFailureException("")).when(gameEnvelope).sendMove(anyLong(), any());
        //When & Then
        mockMvc.perform(put("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).sendMove(anyLong(), any());
    }

    @Test
    public void shouldDeleteGame() throws Exception {
        //Given
        doNothing().when(gameEnvelope).deleteGame(anyLong());
        //When & Then
        mockMvc.perform(delete("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(gameEnvelope, times(1)).deleteGame(any());
    }

    @Test
    public void shouldNotDeleteGame() throws Exception {
        //Given
        doThrow(new MethodFailureException("")).when(gameEnvelope).deleteGame(anyLong());
        //When & Then
        mockMvc.perform(delete("/v1/games/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
        verify(gameEnvelope, times(1)).deleteGame(any());
    }

    @Test
    public void shouldGetFinishedGames() throws Exception {
        //Given
        List<FinishedGame> list = new ArrayList<>();
        list.add(new FinishedGame());
        list.add(new FinishedGame());
        list.add(new FinishedGame());
        when(gameEnvelope.getFinishedGames()).thenReturn(list);
        //When & Then
        mockMvc.perform(get("/v1/games/finished")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
        verify(gameEnvelope, times(1)).getFinishedGames();
    }

}