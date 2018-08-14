package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.BoardDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.MoveDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.RulesSetsDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.BoardMapper;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.RulesSetsMapper;
import com.wawrze.restcheckers.moves.RulesSets;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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

    private Game game;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @RequestMapping(method = RequestMethod.POST, value = "newGame")
    public void startNewGame() throws IncorrectMoveException, IncorrectMoveFormat {
        game = new Game(
                "Rest game",
                rules.getRules().get(0),
                true,
                false
        );
        LOGGER.info("Game created.");
        game.play(restUI);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public BoardDto sendMove(@RequestBody MoveDto moveDto) throws InterruptedException {
        String s = "" + moveDto.getRow1() + moveDto.getCol1() + "-" + moveDto.getRow2() + moveDto.getCol2();
        restUI.getInQueue().push(s);
        TimeUnit.MILLISECONDS.sleep(200);
        LOGGER.info("New move served. Board sent.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
    public BoardDto getBoard() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        LOGGER.info("Board sent.");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getRulesSets")
    public RulesSetsDto getRulesSets() {
        LOGGER.info("Rules sets sent.");
        return rulesSetsMapper.mapToRulesSetsDto(rules);
    }

}