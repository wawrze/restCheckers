package com.wawrze.restcheckers.gameplay.userInterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameDto {

    private String name;
    private String rulesName;
    private String isWhiteAIPlayer;
    private String isBlackAIPlayer;

}