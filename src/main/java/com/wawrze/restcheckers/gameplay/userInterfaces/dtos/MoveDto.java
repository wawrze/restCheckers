package com.wawrze.restcheckers.gameplay.userInterfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MoveDto {

    private char row1;
    private int col1;
    private char row2;
    private int col2;

}