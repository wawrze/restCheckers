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

    public GameListDto mapToGameListDto() {
        return new GameListDto(gameEnvelope.getGames().entrySet().stream()
                .map(entry -> new GameInfoDto(
                        entry.getValue().getName(),
                        entry.getValue().getRulesSet().getName(),
                        entry.getValue().isWhiteAIPlayer(),
                        entry.getValue().isBlackAIPlayer(),
                        entry.getValue().isFinished(),
                        entry.getValue().isDraw(),
                        entry.getValue().isWinner()
                ))
                .collect(Collectors.toList()));
    }

}