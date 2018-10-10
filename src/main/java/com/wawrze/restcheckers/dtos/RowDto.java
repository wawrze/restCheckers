package com.wawrze.restcheckers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RowDto {

    private int name;
    private List<FigureDto> figures;

}