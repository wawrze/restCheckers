package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.RulesSets;
import com.wawrze.restcheckers.dtos.*;
import com.wawrze.restcheckers.dtos.mappers.GameInfoMapper;
import com.wawrze.restcheckers.dtos.mappers.GameListMapper;
import com.wawrze.restcheckers.dtos.mappers.RulesSetsMapper;
import com.wawrze.restcheckers.services.Encryptor;
import com.wawrze.restcheckers.services.dbservices.DBService;
import exceptions.MethodFailureException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Getter
public class GameEnvelope {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEnvelope.class);

    static String SESSION_ID = "sessionId";
    private static int SESSION_TIME = 60 * 60 * 24;

    @Autowired
    GameExecutor gameExecutor;
    @Autowired
    RestUI restUI;
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
    private Encryptor encryptor;

    private Map<Long, Game> games = new HashMap<>();

    public Cookie startNewGame(GameDto gameDto) throws MethodFailureException {
        Game game;
        game = new Game(
                gameDto.getName(),
                dbService.getRulesSetByName(gameDto.getRulesName()),
                gameDto.getIsBlackAIPlayer().equals("true"),
                gameDto.getIsWhiteAIPlayer().equals("true")
        );
        dbService.saveGame(game);
        games.put(game.getId(), game);
        LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") created.");

        String sessionId = encryptor.encrypt("" + game.getId());
        Cookie cookie = new Cookie(SESSION_ID, sessionId);
        cookie.setMaxAge(SESSION_TIME);
        return cookie;
    }

    public void playGame(Cookie[] cookies) throws MethodFailureException {
        Long sessionId = getSessionIdFromCookie(cookies);
        Game game = games.get(sessionId);
        if (game == null) {
            LOGGER.warn("There is no game with id = " + sessionId + "! Game not started!");
            throw new MethodFailureException("There is no game with id = " + sessionId + "! Game not started!");
        }
        LOGGER.info("Starting game \"" + game.getName() + "\" (id " + game.getId() + ").");
        game.updateLastAction();
        gameExecutor.play(game);
    }

    public boolean sendMove(Cookie[] cookies, MoveDto moveDto) throws MethodFailureException {
        String s = moveDto.getMove();
        Long sessionId = getSessionIdFromCookie(cookies);
        Game game = games.get(sessionId);
        if (game == null) {
            game = dbService.getGameById(sessionId);
            if (game == null) {
                LOGGER.warn("There is no game with id = \"" + sessionId + "\"! Move not served!");
                throw new MethodFailureException("There is no game with id = \"" + sessionId + "\"! Move not served!");
            }
        }
        game.getInQueue().offer(s);
        LOGGER.info("New move (game: \"" + game.getName() + "\" (id " + game.getId() + "), move: "
                + moveDto.getMove() + ") served.");
        game.updateLastAction();
        dbService.saveGame(game);
        LOGGER.info("Game: " + game.getName() + " (" + game.getId() + ") saved to database.");
        return true;
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
        if (rulesSet == null) {
            LOGGER.warn("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
            throw new MethodFailureException("There is no rules set named \"" + rulesSetName + "\"! Rules set not sent!");
        }
        LOGGER.info("Rules set \"" + rulesSetName + "\" sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    public GameInfoDto getGameInfo(Cookie[] cookies) throws MethodFailureException {
        Long sessionId = getSessionIdFromCookie(cookies);
        Game game = games.get(sessionId);
        if (game == null) {
            LOGGER.warn("There is no game with id = \"" + sessionId + "\"! Game info not sent!");
            throw new MethodFailureException("There is no game with id = \"" + sessionId + "\"! Game info not sent!");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted exception in getGameInfo: " + e);
            Thread.currentThread().interrupt();
        }
        GameInfoDto gameInfoDto = gameInfoMapper.mapToGameInfoDto(game);
        if (game.isFinished()) {
            games.remove(sessionId);
            game.updateLastAction();
            dbService.saveFinishedGame(game);
            dbService.deleteGame(sessionId);
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") finished. Game info sent. " +
                    "Game archived in DB.");
        } else {
            LOGGER.info("Game \"" + game.getName() + "\" (id " + game.getId() + ") info sent.");
        }
        game.updateLastAction();
        return gameInfoDto;
    }

    public Cookie deleteGame(Cookie[] cookies) throws MethodFailureException {
        Long sessionId = getSessionIdFromCookie(cookies);
        Game game = games.get(sessionId);
        if (game == null) {
            LOGGER.warn("There is no game with id = \"" + sessionId + "\"! Game not removed!");
            throw new MethodFailureException("There is no game with id = \"" + sessionId + "\"! Game not removed!");
        }
        games.remove(sessionId);
        LOGGER.info("Game  \"" + game.getName() + "\" (id " + game.getId() + ") removed.");
        Cookie cookie = new Cookie(SESSION_ID, "");
        cookie.setMaxAge(0);
        return cookie;
    }

    public GameListDto getGameList() {
        LOGGER.info("Game list sent.");
        return gameListMapper.mapToGameListDto();
    }

    public List<FinishedGame> getFinishedGames() {
        List<FinishedGame> finishedGames = dbService.getFinishedGames();
        LOGGER.info("========> List of finished games:");
        finishedGames.forEach(game -> {
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

    private Long getSessionIdFromCookie(Cookie[] cookies) throws MethodFailureException {
        Long sessionId = -1L;
        if (cookies == null || cookies.length == 0) {
            throw new MethodFailureException("");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SESSION_ID)) {
                sessionId = Long.valueOf(encryptor.decrypt(cookie.getValue()));
            }
        }
        return sessionId;
    }

}