package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.gameplay.userInterface.GameEnvelope;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameInfoDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameListMapper {

    @Autowired
    GameEnvelope gameEnvelope;

    @Autowired
    GameInfoMapper gameInfoMapper;

    public GameListDto mapToGameListDto() {
        return new GameListDto(gameEnvelope.getGames().entrySet().stream()
                .map(entry -> entry.getValue())
                .map(game -> {
                    GameInfoDto gameInfoDto = gameInfoMapper.mapToGameProgressDetailsDto(game);
                    gameInfoDto.setBoard(null);
                    return gameInfoDto;
                })
                .collect(Collectors.toList()));
    }

}