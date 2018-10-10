package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.dtos.*;
import exceptions.httpExceptions.MethodFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.GET, value = "/games/{gameId}")
    public GameInfoDto getBoard(@PathVariable Long gameId) throws MethodFailureException {
        return gameEnvelope.getGameInfo(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rules")
    public RulesSetsDto getRulesSets() {
        return gameEnvelope.getRulesSets();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rules/{rulesSetName}")
    public RulesSetDto getRulesSet(@PathVariable String rulesSetName) throws MethodFailureException {
        return gameEnvelope.getRulesSet(rulesSetName);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games/new")
    public Long startNewGame(@RequestBody GameDto gameDto) throws MethodFailureException {
        return gameEnvelope.startNewGame(gameDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games/{gameId}")
    public void playGame(@PathVariable Long gameId) throws MethodFailureException {
        gameEnvelope.playGame(gameId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/games/{gameId}")
    public GameInfoDto sendMove(@PathVariable Long gameId, @RequestBody MoveDto moveDto) throws MethodFailureException {
        return gameEnvelope.sendMove(gameId, moveDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/games/{gameId}")
    public void deleteGame(@PathVariable Long gameId) throws MethodFailureException {
        gameEnvelope.deleteGame(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    public GameListDto getGames() {
        return gameEnvelope.getGameList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games/finished")
    public List<FinishedGame> getFinishedGames() {
        return gameEnvelope.getFinishedGames();
    }

}