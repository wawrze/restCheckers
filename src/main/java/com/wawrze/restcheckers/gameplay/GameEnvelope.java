package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.RulesSets;
import com.wawrze.restcheckers.dtos.*;
import com.wawrze.restcheckers.dtos.mappers.GameInfoMapper;
import com.wawrze.restcheckers.dtos.mappers.GameListMapper;
import com.wawrze.restcheckers.dtos.mappers.RulesSetsMapper;
import com.wawrze.restcheckers.services.dbservices.DBService;
import exceptions.httpExceptions.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void playGame(Long gameId) throws MethodFailureException {
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
            game = dbService.getGameById(gameId);
            if(game == null) {
                LOGGER.warn("There is no game with id = \"" + gameId + "\"! Move not served!");
                throw new MethodFailureException("There is no game with id = \"" + gameId + "\"! Move not served!");
            }
        }
        game.getInQueue().offer(s);
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
            throw new MethodFailureException("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
        }
        LOGGER.info("Rules set \"" + rulesSetName + "\" sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    public GameInfoDto getGameInfo(Long gameId) throws MethodFailureException {
        Game game = games.get(gameId);
        if(game == null) {
            LOGGER.warn("There is no game with id = \"" + gameId + "\"! Game info not sent!");
            throw new MethodFailureException("There is no game with id = \"" + gameId + "\"! Game info not sent!");
        }
        try {
            Thread.sleep(500L);
        }
        catch(InterruptedException e) {
            throw new MethodFailureException("Application error!");
        }
        GameInfoDto gameInfoDto = gameInfoMapper.mapToGameProgressDetailsDto(game);
        if(game.isFinished()) {
            games.remove(gameId);
            game.updateLastAction();
            dbService.saveFinishedGame(game);
            dbService.deleteGame(gameId);
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") finished. Game info sent. " +
                    "Game archived in DB.");
        }
        else {
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") info sent.");
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

    public List<FinishedGame> getFinishedGames() {
        List<FinishedGame> finishedGames = dbService.getFinishedGames();
        LOGGER.info("========> List of finished games:");
        finishedGames.stream()
                .forEach(game -> {
                    LOGGER.info("\tGame #" + game.getId() + ": \"" + game.getName() + "\"");
                    LOGGER.info("\t\tWinner/type of victory: " + game.getTypeOfVictoryAndWinner());
                    LOGGER.info("\t\tNumber of moves: " + game.getMoves());
                    LOGGER.info("\t\tRules set name: " + game.getRulesSet().getName());
                    LOGGER.info("\t\tBlack player: " + (game.isBlackAIPlayer() ? "computer" : "human"));
                    LOGGER.info("\t\tWhite player: " + (game.isWhiteAIPlayer() ? "computer" : "human"));
                    LOGGER.info("\t\tStart time: " + gameInfoMapper.mapDateTimeToString(game.getStartTime()));
                    LOGGER.info("\t\tFinish time: " + gameInfoMapper.mapDateTimeToString(game.getFinishTime()));
                });
        LOGGER.info("<================================");
        return finishedGames;
    }

}