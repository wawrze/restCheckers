package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.dtos.*;
import exceptions.MethodFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class GameController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    GameEnvelope gameEnvelope;

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    public GameInfoDto getBoard(HttpServletRequest request) throws MethodFailureException {
        return gameEnvelope.getGameInfo(request.getCookies());
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
    public void startNewGame(@RequestBody GameDto gameDto, HttpServletResponse response) throws MethodFailureException {
        response.addCookie(gameEnvelope.startNewGame(gameDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    public void playGame(HttpServletRequest request) throws MethodFailureException {
        gameEnvelope.playGame(request.getCookies());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/games")
    public boolean sendMove(@RequestBody MoveDto moveDto, HttpServletRequest request) throws MethodFailureException {
        return gameEnvelope.sendMove(request.getCookies(), moveDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/games")
    public void deleteGame(HttpServletRequest request, HttpServletResponse response) throws MethodFailureException {
        response.addCookie(gameEnvelope.deleteGame(request.getCookies()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games/all")
    public GameListDto getGames() {
        return gameEnvelope.getGameList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games/finished")
    public List<FinishedGame> getFinishedGames() {
        return gameEnvelope.getFinishedGames();
    }

}