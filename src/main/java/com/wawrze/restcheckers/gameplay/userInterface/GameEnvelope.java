package com.wawrze.restcheckers.gameplay.userInterface;

import com.wawrze.restcheckers.gameplay.*;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.*;
import com.wawrze.restcheckers.gameplay.userInterface.mappers.*;
import com.wawrze.restcheckers.service.dbservices.DBService;
import exceptions.httpExceptions.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Getter
public class GameEnvelope {

    @Autowired
    private DBService dbService;

    @Autowired
    private RulesSets rules;

    @Autowired
    private RulesSetsMapper rulesSetsMapper;

    @Autowired
    private GameInfoMapper gameInfoMapper;

    @Autowired
    private GameListMapper gameListMapper;

    @Autowired
    GameExecutor gameExecutor;

    @Autowired
    RestUI restUI;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEnvelope.class);

    private Map<Long, Game> games = new HashMap<>();

    public Long startNewGame(GameDto gameDto) throws MethodFailureException {
        Game game;
        game = new Game(
                gameDto.getName(),
                dbService.getRulesSetByName(gameDto.getRulesName()),
                gameDto.getIsBlackAIPlayer().equals("true"),
                gameDto.getIsWhiteAIPlayer().equals("true")
        );
        if(game == null) {
            LOGGER.warn("Unknown error! Game not created!");
            throw new MethodFailureException("Unknown error! Game not created!");
        }
        dbService.saveGame(game);
        games.put(game.getId(), game);
        LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") created.");
        return game.getId();
    }

    public void playGame(Long gameId) throws ForbiddenException {
        Game game = games.get(gameId);
        if(game == null) {
            LOGGER.warn("There is no game with id = " + gameId + "! Game not started!");
            throw new MethodFailureException("There is no game with id = " + gameId + "! Game not started!");
        }
        LOGGER.info("Starting game \"" + game.getName() + "\" (id " + game.getId() + ").");
        game.updateLastAction();
        gameExecutor.play(game);
    }

    public GameInfoDto sendMove(Long gameId, MoveDto moveDto) throws MethodFailureException {
        String s = moveDto.getMove();
        Game game = games.get(gameId);
        if(game == null) {
            LOGGER.warn("There is no game with id = \"" + gameId + "\"! Move not served!");
            throw new MethodFailureException("There is no game with id = \"" + gameId + "\"! Move not served!");
        }
        game.getInQueue().offer(s);
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        }
        catch(InterruptedException e) {
            LOGGER.warn("SLEEP Exception: " + e.getMessage());
        }
        LOGGER.info("New move (game: \"" + game.getName() + "\" (id " + game.getId() + "), move: "
                + moveDto.getMove() + ") served.");
        game.updateLastAction();
        dbService.saveGame(game);
        LOGGER.info("Game: " + game.getName() + " (" + game.getId() + ") saved to database.");
        return gameInfoMapper.mapToGameProgressDetailsDto(game);
    }

    public RulesSetsDto getRulesSets() {
        LOGGER.info("Rules sets sent.");
        return rulesSetsMapper.mapToRulesSetsDto();
    }

    public RulesSetDto getRulesSet(String rulesSetName) throws MethodFailureException {
        List<RulesSet> list = rules.updateRules().stream()
                .filter(rule -> rule.getName().equals(rulesSetName))
                .collect(Collectors.toList());
        RulesSet rulesSet = list.size() == 0 ? null : list.get(0);
        if(rulesSet == null) {
            LOGGER.warn("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
            throw new ForbiddenException();
        }
        LOGGER.info("Rules set \"" + rulesSetName + "\" sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    public GameInfoDto getGameInfo(Long gameId) throws MethodFailureException {
        Game game = games.get(gameId);
        if(game == null) {
            LOGGER.warn("There is no game with id = \"" + gameId + "\"! Game details not sent!");
            throw new MethodFailureException("There is no game with id = \"" + gameId + "\"! Game details not sent!");
        }
        GameInfoDto gameInfoDto = gameInfoMapper.mapToGameProgressDetailsDto(game);
        if(game.isFinished()) {
            games.remove(gameId);
            game.updateLastAction();
            dbService.saveGame(game);
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") finish details sent. " +
                    "Game archived to DB.");
        }
        else {
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") details sent.");
        }
        game.updateLastAction();
        return gameInfoDto;
    }

    public void deleteGame(Long gameId) throws MethodFailureException {
        Game game = games.get(gameId);
        if(game == null) {
            LOGGER.warn("There is no game with id = \"" + gameId + "\"! Game not removed!");
            throw new MethodFailureException("There is no game with id = \"" + gameId + "\"! Game not removed!");
        }
        games.remove(gameId);
        LOGGER.info("Game  \"" + game.getName() + "\" (id " + game.getId() + ") removed.");
    }

    public GameListDto getGameList() {
        LOGGER.info("Game list sent.");
        return gameListMapper.mapToGameListDto();
    }

}