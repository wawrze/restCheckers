package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import exceptions.httpExceptions.ForbiddenException;
import exceptions.httpExceptions.MethodFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.GET, value = "getGameInfo")
    public GameInfoDto getBoard(@RequestParam Long gameId) throws ForbiddenException {
        return gameEnvelope.getGameInfo(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        return gameEnvelope.getRulesSets();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSet")
    public RulesSetDto getRulesSet(@RequestParam String rulesSetName) throws ForbiddenException {
        return gameEnvelope.getRulesSet(rulesSetName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGames")
    public GameListDto getGames() {
        return gameEnvelope.getGameList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "newGame")
    public Long startNewGame(@RequestBody GameDto gameDto) throws ForbiddenException, MethodFailureException {
        return gameEnvelope.startNewGame(gameDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "playGame")
    public void playGame(@RequestParam Long gameId) throws ForbiddenException {
        gameEnvelope.playGame(gameId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public GameInfoDto sendMove(@RequestParam Long gameId, @RequestBody MoveDto moveDto) throws ForbiddenException {
        return gameEnvelope.sendMove(gameId, moveDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteGame")
    public void deleteGame(@RequestParam Long gameId) throws ForbiddenException {
        gameEnvelope.deleteGame(gameId);
    }

}