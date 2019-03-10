package com.wawrze.restcheckers.dtos.mappers;

import com.wawrze.restcheckers.dtos.GameListDto;
import com.wawrze.restcheckers.gameplay.GameEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameListMapper {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    GameEnvelope gameEnvelope;

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    GameInfoMapper gameInfoMapper;

    public GameListDto mapToGameListDto() {
        return new GameListDto(gameEnvelope.getGames().values().stream()
                .map(gameInfoMapper::mapToGameInfoDto)
                .collect(Collectors.toList()));
    }

}