package com.wawrze.restcheckers.gameplay.userInterface.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DateTimeDto {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

}