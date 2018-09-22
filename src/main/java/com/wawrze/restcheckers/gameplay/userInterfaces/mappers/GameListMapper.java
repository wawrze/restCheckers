package com.wawrze.restcheckers.gameplay.userInterfaces.mappers;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.userInterfaces.GameEnvelope;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.GameInfoDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.GameListDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.GameProgressDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GameListMapper {

    @Autowired
    GameEnvelope gameEnvelope;

    @Autowired
    GameProgressDetailsMapper gameProgressDetailsMapper;

    public GameListDto mapToGameListDto() {
        List<GameInfoDto> list = new ArrayList<>();
        for(Map.Entry<String, Game> entry : gameEnvelope.getGames().entrySet()) {
            Game game = entry.getValue();
            GameProgressDetailsDto gameProgressDetailsDto = gameProgressDetailsMapper.mapToGameProgressDetailsDto(game);
            list.add(new GameInfoDto(
                    game.getName(),
                    game.getRulesSet().getName(),
                    game.isWhiteAIPlayer(),
                    game.isBlackAIPlayer(),
                    game.isFinished(),
                    game.isDraw(),
                    game.isWinner(),
                    gameProgressDetailsDto.getMoves(),
                    gameProgressDetailsDto.getBlackQueenMoves(),
                    gameProgressDetailsDto.getWhiteQueenMoves(),
                    gameProgressDetailsDto.getWhitePawns(),
                    gameProgressDetailsDto.getBlackPawns(),
                    gameProgressDetailsDto.getWhiteQueens(),
                    gameProgressDetailsDto.getBlackQueens()
            ));
        }
        return new GameListDto(list);
    }

}