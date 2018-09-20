package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.*;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.POST, value = "newGame", consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public void startNewGame(@RequestBody GameDto gameDto) throws IncorrectMoveException, IncorrectMoveFormat {
        gameEnvelope.startNewGame(gameDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public BoardDto sendMove(@RequestParam String gameName, @RequestBody MoveDto moveDto) throws InterruptedException {
        return gameEnvelope.sendMove(gameName, moveDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
    public BoardDto getBoard(@RequestParam String gameName) {
        return gameEnvelope.getBoard(gameName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        return gameEnvelope.getRulesSets();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSet")
    public RulesSetDto getRulesSet(@RequestParam String rulesSetName) {
        return gameEnvelope.getRulesSet(rulesSetName);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGameProgressDetails")
    public GameProgressDetailsDto getGameProgressDetails(@RequestParam String gameName) {
        return gameEnvelope.getGameProgressDetails(gameName);
    }

}