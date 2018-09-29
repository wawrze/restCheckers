package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.gameplay.RulesSet;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.RulesSetDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.RulesSetsDto;
import com.wawrze.restcheckers.gameplay.RulesSets;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RulesSetsMapper {

    public RulesSetsDto mapToRulesSetsDto(RulesSets rulesSets) {
        return new RulesSetsDto(rulesSets.getRules().stream()
                .map(this::mapToRulesSetDto)
                .collect(Collectors.toList()));
    }

    public RulesSetDto mapToRulesSetDto(RulesSet rulesSet) {
        return new RulesSetDto(
                rulesSet.isVictoryConditionsReversed(),
                rulesSet.isQueenRangeOne(),
                rulesSet.isCaptureAny(),
                rulesSet.isPawnMoveBackward(),
                rulesSet.isPawnCaptureBackward(),
                rulesSet.isQueenRangeOneAfterCapture(),
                rulesSet.getName(),
                rulesSet.getDescription()
        );
    }

}