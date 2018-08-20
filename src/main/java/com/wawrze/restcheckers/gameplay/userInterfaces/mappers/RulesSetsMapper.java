package com.wawrze.restcheckers.gameplay.userInterfaces.mappers;

import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.RulesSetDto;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.RulesSetsDto;
import com.wawrze.restcheckers.moves.RulesSets;
import org.springframework.stereotype.Component;

@Component
public class RulesSetsMapper {

    private int id;

    public RulesSetsDto mapToRulesSetsDto(RulesSets rulesSets) {
        id = -1;
        RulesSetsDto rulesSetsDto = new RulesSetsDto();
        rulesSets.getRules().forEach(rule -> rulesSetsDto.getRules().add(mapToRulesSetDto(rule)));
        return rulesSetsDto;
    }

    private RulesSetDto mapToRulesSetDto(RulesSet rulesSet) {
        id++;
        return new RulesSetDto(
                rulesSet.isVictoryConditionsReversed(),
                rulesSet.isQueenRangeOne(),
                rulesSet.isCaptureAny(),
                rulesSet.isPawnMoveBackward(),
                rulesSet.isPawnCaptureBackward(),
                rulesSet.isQueenRangeOneAfterCapture(),
                rulesSet.getName(),
                rulesSet.getDescription(),
                id
        );
    }

}