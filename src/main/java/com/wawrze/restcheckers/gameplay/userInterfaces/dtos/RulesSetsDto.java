package com.wawrze.restcheckers.gameplay.userInterfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RulesSetsDto {

    private List<RulesSetDto> rules = new ArrayList<>();

}