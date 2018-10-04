package com.wawrze.restcheckers.gameplay.userInterface;

import com.google.gson.Gson;
import com.wawrze.restcheckers.gameplay.RulesSets;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.RulesSetsMapper;
import exceptions.httpExceptions.ForbiddenException;
import exceptions.httpExceptions.MethodFailureException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameEnvelope gameEnvelope;

    private static int counter = 1;

    private RulesSetsMapper rulesSetsMapper;
    private RulesSets rulesSets;
    private Gson gson;

    @BeforeClass
    public static void beforeTests(){
        System.out.println("Controller tests: started");
    }

    @AfterClass
    public static void afterTests(){
        System.out.println("Controller tests: finished");
    }

    @Before
    public void before(){
        System.out.println("Test #" + counter + ": started");
        rulesSetsMapper = new RulesSetsMapper();
        rulesSets = new RulesSets();
        gson = new Gson();
    }

    @After
    public void after(){
        System.out.println("Test #" + counter + ": finished");
        counter++;
    }


    @Test
    public void shouldGetBoard() throws Exception {
        //Given
        BoardDto boardDto = new BoardDto(
                null,
                "status",
                true,
                false,
                false,
                null
        );
        when(gameEnvelope.getBoard(anyString())).thenReturn(boardDto);
        //When & Then
        mockMvc.perform(get("/game/getBoard?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is("status")))
                .andExpect(jsonPath("$.activePlayer", is(true)))
                .andExpect(jsonPath("$.whiteAIPlayer", is(false)))
                .andExpect(jsonPath("$.blackAIPlayer", is(false)));
        verify(gameEnvelope, times(1)).getBoard(any());
    }

    @Test
    public void shouldNotGetBoard() throws Exception {
        //Given
        when(gameEnvelope.getBoard(anyString())).thenThrow(new ForbiddenException());
        //When & Then
        mockMvc.perform(get("/game/getBoard?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).getBoard(any());
    }

    @Test
    public void shouldGetRulesSets() throws Exception {
        //Given
        rulesSets = new RulesSets();
        rulesSetsMapper = new RulesSetsMapper();
        RulesSetsDto rulesSetsDto = rulesSetsMapper.mapToRulesSetsDto(rulesSets);
        when(gameEnvelope.getRulesSets()).thenReturn(rulesSetsDto);
        //When & Then
        mockMvc.perform(get("/game/getRulesSets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.rules", hasSize(3)))
                .andExpect(jsonPath("$.rules[0].name", is("classic")))
                .andExpect(jsonPath("$.rules[1].name", is("english")))
                .andExpect(jsonPath("$.rules[2].name", is("poddavki")));
        verify(gameEnvelope, times(1)).getRulesSets();
    }

    @Test
    public void shouldGetRulesSet() throws Exception {
        //Given
        rulesSets = new RulesSets();
        rulesSetsMapper = new RulesSetsMapper();
        RulesSetDto rulesSetDto = rulesSetsMapper.mapToRulesSetDto(rulesSets.updateRules().get(0));
        when(gameEnvelope.getRulesSet("classic")).thenReturn(rulesSetDto);
        //When & Then
        mockMvc.perform(get("/game/getRulesSet?rulesSetName=classic")
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
        when(gameEnvelope.getRulesSet("classic")).thenThrow(new ForbiddenException());
        //When & Then
        mockMvc.perform(get("/game/getRulesSet?rulesSetName=classic")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).getRulesSet(any());
    }

    @Test
    public void shouldGetGamesDetails() throws Exception {
        //Given
        GameProgressDetailsDto gameProgressDetailsDto = new GameProgressDetailsDto(
                true,
                true,
                true,
                50,
                15,
                15,
                5,
                5,
                1,
                2
        );
        when(gameEnvelope.getGameProgressDetails(anyString())).thenReturn(gameProgressDetailsDto);
        //When & Then
        mockMvc.perform(get("/game/getGameProgressDetails?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.finished", is(true)))
                .andExpect(jsonPath("$.moves", is(50)))
                .andExpect(jsonPath("$.blackPawns", is(5)))
                .andExpect(jsonPath("$.whiteQueens", is(1)));
        verify(gameEnvelope, times(1)).getGameProgressDetails(any());
    }

    @Test
    public void shouldNotGetGamesDetails() throws Exception {
        //Given
                when(gameEnvelope.getGameProgressDetails(anyString())).thenThrow(new ForbiddenException());
        //When & Then
        mockMvc.perform(get("/game/getGameProgressDetails?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).getGameProgressDetails(any());
    }

    @Test
    public void shouldGetGames() throws Exception {
        //When
        List<GameInfoDto> gameList = new ArrayList<>();
        gameList.add(new GameInfoDto(
                "some name",
                "classic",
                false,
                false,
                false,
                false,
                false,
                new DateTimeDto()
        ));
        gameList.add(new GameInfoDto(
                "some other name",
                "english",
                true,
                true,
                true,
                true,
                true,
                new DateTimeDto()
        ));
        gameList.add(new GameInfoDto());
        GameListDto gameListDto = new GameListDto(gameList);
        when(gameEnvelope.getGameList()).thenReturn(gameListDto);
        //When & Then
        mockMvc.perform(get("/game/getGames")
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
        mockMvc.perform(post("/game/newGame")
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
        doThrow(new ForbiddenException()).when(gameEnvelope).startNewGame(any());
        //When & Then
        mockMvc.perform(post("/game/newGame")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).startNewGame(any());
    }

    @Test
    public void shouldNotStartNewGame2() throws Exception {
        //Given
        GameDto gameDto = new GameDto(
                "some name",
                "classic",
                "false",
                "false"
        );
        String jsonContent = gson.toJson(gameDto);
        doThrow(new MethodFailureException()).when(gameEnvelope).startNewGame(any());
        //When & Then
        mockMvc.perform(post("/game/newGame")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(420));
        verify(gameEnvelope, times(1)).startNewGame(any());
    }

    @Test
    public void shouldServeNewMove() throws Exception {
        //Given
        MoveDto moveDto = new MoveDto("A1-B2");
        BoardDto boardDto = new BoardDto(
                null,
                "status",
                true,
                false,
                false,
                null
        );
        String jsonContent = gson.toJson(moveDto);
        when(gameEnvelope.sendMove("someName", moveDto)).thenReturn(boardDto);
        //When & Then
        mockMvc.perform(post("/game/sendMove?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
        verify(gameEnvelope, times(1)).sendMove(anyString(), any());
    }

    @Test
    public void shouldNotServeNewMove() throws Exception {
        //Given
        MoveDto moveDto = new MoveDto("A1-B2");
        String jsonContent = gson.toJson(moveDto);
        when(gameEnvelope.sendMove(anyString(), any())).thenThrow(new ForbiddenException());
        //When & Then
        mockMvc.perform(post("/game/sendMove?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).sendMove(anyString(), any());
    }

    @Test
    public void shouldDeleteGame() throws Exception {
        //When & Then
        mockMvc.perform(delete("/game/deleteGame?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(gameEnvelope, times(1)).deleteGame(any());
    }

    @Test
    public void shouldNotDeleteGame() throws Exception {
        //Given
        doThrow(new ForbiddenException()).when(gameEnvelope).deleteGame(anyString());
        //When & Then
        mockMvc.perform(delete("/game/deleteGame?gameName=someName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        verify(gameEnvelope, times(1)).deleteGame(any());
    }

}