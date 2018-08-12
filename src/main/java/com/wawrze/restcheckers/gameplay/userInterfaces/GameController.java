package com.wawrze.restcheckers.gameplay.userInterfaces;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.BoardDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.MoveDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.mappers.BoardMapper;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    RestUI restUI;

    @Autowired
    BoardMapper boardMapper;

    private Game game;

    @RequestMapping(method = RequestMethod.POST, value = "newGame")
    public void startNewGame() throws IncorrectMoveException, IncorrectMoveFormat {
        game = new Game(
                "Rest game",
                new RulesSet(
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        "classic",
                        "classic (brasilian) draughts"
                ),
                true,
                false
        );
        game.play(restUI);
    }

    @RequestMapping(method = RequestMethod.POST, value = "sendMove")
    public BoardDto sendMove(@RequestBody MoveDto moveDto) throws InterruptedException {
        String s = "" + moveDto.getRow1() + moveDto.getCol1() + "-" + moveDto.getRow2() + moveDto.getCol2();
        restUI.getInQueue().push(s);
        TimeUnit.MILLISECONDS.sleep(200);
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBoard")
    public BoardDto getBoard() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println("REQUEST");
        return boardMapper.mapToBoardDto(game.getBoard(), restUI.getGameStatus());
    }

}