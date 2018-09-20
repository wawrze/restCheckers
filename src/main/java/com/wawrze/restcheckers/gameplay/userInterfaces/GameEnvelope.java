package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.*;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.BoardMapper;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.GameProgressDetailsMapper;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.RulesSetsMapper;
import com.wawrze.restcheckers.moves.RulesSets;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GameEnvelope {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    RulesSets rules;

    @Autowired
    RulesSetsMapper rulesSetsMapper;

    @Autowired
    GameProgressDetailsMapper gameProgressDetailsMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEnvelope.class);

    private Map<String, RestUI> restUIs = new HashMap<>();
    private Map<String, Game> games = new HashMap<>();

    public void startNewGame(GameDto gameDto) throws IncorrectMoveException, IncorrectMoveFormat {
        for(Map.Entry<String, Game> g : games.entrySet()) {
            if(g.getKey().equalsIgnoreCase(gameDto.getName())) {
                LOGGER.warn("Game " + gameDto.getName() + " already exist! Game not created.");
                return;
            }
        }
        Game game;
        RestUI restUI = new RestUI();
        RulesSet rulesSet = null;
        for(RulesSet r : rules.getRules()) {
            if(r.getName().equals(gameDto.getRulesName()))
                rulesSet = r;
        }
        boolean isBlackAIPlayer = gameDto.getIsBlackAIPlayer().equals("true");
        boolean isWhiteAIPlayer = gameDto.getIsWhiteAIPlayer().equals("true");
        game = new Game(
                gameDto.getName(),
                rulesSet,
                isBlackAIPlayer,
                isWhiteAIPlayer
        );
        if(game == null) {
            LOGGER.warn("Unknown error! Game not created!");
            return;
        }
        games.put(gameDto.getName(), game);
        restUIs.put(gameDto.getName(), restUI);
        LOGGER.info("Game created.");
        game.play(restUI);
    }

    public BoardDto sendMove(String gameName, MoveDto moveDto) throws InterruptedException {
        String s = moveDto.getMove();
        RestUI restUI = restUIs.get(gameName);
        Game game = games.get(gameName);
        restUI.getInQueue().push(s);
        TimeUnit.MILLISECONDS.sleep(200);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Move not served!");
            return null;
        }
        LOGGER.info("New move (game: " + gameName + ", move: " + moveDto.getMove() + ") served.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    public BoardDto getBoard(String gameName) {
        RestUI restUI = restUIs.get(gameName);
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Board not sent!");
            return null;
        }
        LOGGER.info("Board sent.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    public RulesSetsDto getRulesSets() {
        LOGGER.info("Rules sets sent.");
        return rulesSetsMapper.mapToRulesSetsDto(rules);
    }

    public RulesSetDto getRulesSet(String rulesSetName) {
        RulesSet rulesSet = null;
        for(RulesSet r : rules.getRules()) {
            if(r.getName().equals(rulesSetName))
                rulesSet = r;
        }
        if(rulesSet == null) {
            LOGGER.warn("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
            return null;
        }
        LOGGER.info("Rules set sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    public GameProgressDetailsDto getGameProgressDetails(String gameName) {
        Game game = games.get(gameName);
        if(game == null) {
            LOGGER.warn("There is no game named \"" + gameName + "\"! Game details not sent!");
            return null;
        }
        GameProgressDetailsDto gameProgressDetailsDto = gameProgressDetailsMapper.mapToGameProgressDetailsDto(game);
        if(game.isFinished()) {
            games.remove(gameName);
            LOGGER.info("Game finish details sent. Game removed.");
        }
        else {
            LOGGER.info("Game details sent.");
        }
        return gameProgressDetailsDto;
    }

}