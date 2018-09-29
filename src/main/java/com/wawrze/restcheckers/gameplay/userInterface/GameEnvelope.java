package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.BoardMapper;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.GameListMapper;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.GameProgressDetailsMapper;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.RulesSetsMapper;
import com.wawrze.restcheckers.gameplay.RulesSets;
import exceptions.httpExceptions.ForbiddenException;
import exceptions.httpExceptions.MethodFailureException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Getter
public class GameEnvelope {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    RulesSets rules;

    @Autowired
    RulesSetsMapper rulesSetsMapper;

    @Autowired
    GameProgressDetailsMapper gameProgressDetailsMapper;

    @Autowired
    GameListMapper gameListMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEnvelope.class);

    private Map<String, RestUI> restUIs = new HashMap<>();
    private Map<String, Game> games = new HashMap<>();

    public void startNewGame(GameDto gameDto) throws ForbiddenException, MethodFailureException {
        if(games.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(gameDto.getName()))
                .count() != 0) {
            restUIs.get(gameDto.getName()).gameAlreadyExist(gameDto.getName());
            LOGGER.warn("Game \"" + gameDto.getName() + "\" already exist! Game not created.");
            throw new ForbiddenException();
        }
        RulesSet rulesSet = rules.getRules().stream()
                .filter(rule -> rule.getName().equals(gameDto.getRulesName()))
                .collect(Collectors.toList()).get(0);
        boolean isBlackAIPlayer = gameDto.getIsBlackAIPlayer().equals("true");
        boolean isWhiteAIPlayer = gameDto.getIsWhiteAIPlayer().equals("true");
        Game game = new Game(
                gameDto.getName(),
                rulesSet,
                isBlackAIPlayer,
                isWhiteAIPlayer
        );
        if(game == null) {
            LOGGER.warn("Unknown error! Game not created!");
            throw new MethodFailureException();
        }
        games.put(gameDto.getName(), game);
        RestUI restUI = new RestUI();
        restUIs.put(gameDto.getName(), restUI);
        LOGGER.info("Game \"" + game.getName() + "\"created.");
        game.play(restUI);
    }

    public BoardDto sendMove(String gameName, MoveDto moveDto) throws ForbiddenException {
        String s = moveDto.getMove();
        RestUI restUI = restUIs.get(gameName);
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Move not served!");
            throw new ForbiddenException();
        }
        restUI.getInQueue().offer(s);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        }
        catch(InterruptedException e) {
            LOGGER.warn("SLEEP Exception: " + e.getMessage());
        }
        LOGGER.info("New move (game: " + gameName + ", move: " + moveDto.getMove() + ") served.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    public BoardDto getBoard(String gameName) throws ForbiddenException {
        RestUI restUI = restUIs.get(gameName);
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Board not sent!");
            throw new ForbiddenException();
        }
        LOGGER.info("Board (game \"" + gameName + "\") sent.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    public RulesSetsDto getRulesSets() {
        LOGGER.info("Rules sets sent.");
        return rulesSetsMapper.mapToRulesSetsDto(rules);
    }

    public RulesSetDto getRulesSet(String rulesSetName) throws ForbiddenException {
        RulesSet rulesSet = rules.getRules().stream()
                .filter(rule -> rule.getName().equals(rulesSetName))
                .collect(Collectors.toList()).get(0);
        if(rulesSet == null) {
            LOGGER.warn("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
            throw new ForbiddenException();
        }
        LOGGER.info("Rules set \"" + rulesSetName + "\" sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    public GameProgressDetailsDto getGameProgressDetails(String gameName) throws ForbiddenException {
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Game details not sent!");
            throw new ForbiddenException();
        }
        GameProgressDetailsDto gameProgressDetailsDto = gameProgressDetailsMapper.mapToGameProgressDetailsDto(game);
        if(game.isFinished()) {
            games.remove(gameName);
            restUIs.remove(gameName);
            LOGGER.info("Game \"" + gameName + "\" finish details sent. Game removed.");
        }
        else {
            LOGGER.info("Game \"" + gameName + "\" details sent.");
        }
        return gameProgressDetailsDto;
    }

    public void deleteGame(String gameName) throws ForbiddenException {
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Game not removed!");
            throw new ForbiddenException();
        }
        games.remove(gameName);
        restUIs.remove(gameName);
        LOGGER.info("Game  \"" + gameName + "\" removed.");
    }

    public GameListDto getGameList() {
        LOGGER.info("Game list sent.");
        return gameListMapper.mapToGameListDto();
    }

}