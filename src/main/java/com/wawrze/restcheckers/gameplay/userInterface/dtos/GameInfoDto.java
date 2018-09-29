package com.wawrze.restcheckers.gameplay.userInterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameInfoDto {

    private String name;
    private String rulesName;
    private boolean isWhiteAIPlayer;
    private boolean isBlackAIPlayer;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;

}