package com.wawrze.restcheckers.gameplay.userInterfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameListDto {

    List<GameInfoDto> gamesList;

}