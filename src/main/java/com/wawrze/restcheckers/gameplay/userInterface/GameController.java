package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import exceptions.ForbiddenException;
import exceptions.MethodFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.POST, value = "newGame")
    public void startNewGame(@RequestBody GameDto gameDto) throws ForbiddenException, MethodFailureException {
        gameEnvelope.startNewGame(gameDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public BoardDto sendMove(@RequestParam String gameName, @RequestBody MoveDto moveDto) throws ForbiddenException {
        return gameEnvelope.sendMove(gameName, moveDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
    public BoardDto getBoard(@RequestParam String gameName) throws ForbiddenException {
        return gameEnvelope.getBoard(gameName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        return gameEnvelope.getRulesSets();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSet")
    public RulesSetDto getRulesSet(@RequestParam String rulesSetName) throws ForbiddenException {
        return gameEnvelope.getRulesSet(rulesSetName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGameProgressDetails")
    public GameProgressDetailsDto getGameProgressDetails(@RequestParam String gameName) throws ForbiddenException {
        return gameEnvelope.getGameProgressDetails(gameName);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteGame")
    public void deleteGame(@RequestParam String gameName) throws ForbiddenException {
        gameEnvelope.deleteGame(gameName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGames")
    public GameListDto getGames() throws ForbiddenException {
        return gameEnvelope.getGameList();
    }

}