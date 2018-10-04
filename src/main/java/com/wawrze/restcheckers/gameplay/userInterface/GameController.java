package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.FinishedGame;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import exceptions.httpExceptions.MethodFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.GET, value = "getGameInfo")
    public GameInfoDto getBoard(@RequestParam Long gameId) throws MethodFailureException {
        return gameEnvelope.getGameInfo(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        return gameEnvelope.getRulesSets();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSet")
    public RulesSetDto getRulesSet(@RequestParam String rulesSetName) throws MethodFailureException {
        return gameEnvelope.getRulesSet(rulesSetName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "newGame")
    public Long startNewGame(@RequestBody GameDto gameDto) throws MethodFailureException {
        return gameEnvelope.startNewGame(gameDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "playGame")
    public void playGame(@RequestParam Long gameId) throws MethodFailureException {
        gameEnvelope.playGame(gameId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public GameInfoDto sendMove(@RequestParam Long gameId, @RequestBody MoveDto moveDto) throws MethodFailureException {
        return gameEnvelope.sendMove(gameId, moveDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteGame")
    public void deleteGame(@RequestParam Long gameId) throws MethodFailureException {
        gameEnvelope.deleteGame(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGames")
    public GameListDto getGames() {
        return gameEnvelope.getGameList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getFinishedGames")
    public List<FinishedGame> getFinishedGames() {
        return gameEnvelope.getFinishedGames();
    }

}