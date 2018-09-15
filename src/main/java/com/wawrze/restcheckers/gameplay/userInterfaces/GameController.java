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
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    RestUI restUI;

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    RulesSets rules;

    @Autowired
    RulesSetsMapper rulesSetsMapper;

    @Autowired
    GameProgressDetailsMapper gameProgressDetailsMapper;

    private Game game;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @RequestMapping(method = RequestMethod.POST, value = "newGame", consumes = APPLICATION_JSON_VALUE)
    public void startNewGame(@RequestBody GameDto gameDto) throws IncorrectMoveException, IncorrectMoveFormat {
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
            LOGGER.warn("Game not created!");
            return;
        }
        LOGGER.info("Game created.");
        game.play(restUI);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public BoardDto sendMove(@RequestBody MoveDto moveDto) throws InterruptedException {
        String s = moveDto.getMove();
        restUI.getInQueue().push(s);
        TimeUnit.MILLISECONDS.sleep(200);
        LOGGER.info("New move served.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
    public BoardDto getBoard() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        if(game == null) {
            LOGGER.warn("Board not sent - no game started!");
            return null;
        }
        LOGGER.info("Board sent.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus(), game.isActivePlayer(),
                game.isWhiteAIPlayer(), game.isBlackAIPlayer(), game.getMoves());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        LOGGER.info("Rules sets sent.");
        return rulesSetsMapper.mapToRulesSetsDto(rules);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSet")
    public RulesSetDto getRulesSet(@RequestParam String rulesSetName) {
        RulesSet rulesSet = null;
        for(RulesSet r : rules.getRules()) {
            if(r.getName().equals(rulesSetName))
                rulesSet = r;
        }
        LOGGER.info("Rules set sent.");
        return rulesSetsMapper.mapToRulesSetDto(rulesSet);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getGameProgressDetails")
    public GameProgressDetailsDto getGameProgressDetails() {
        LOGGER.info("Game details sent.");
        return gameProgressDetailsMapper.mapToGameProgressDetailsDto(game);
    }

}