package com.wawrze.restcheckers.dtos.mappers;

import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.dtos.RulesSetDto;
import com.wawrze.restcheckers.dtos.RulesSetsDto;
import com.wawrze.restcheckers.domain.RulesSets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RulesSetsMapper {

    @Autowired
    RulesSets rulesSets;

    public RulesSetsDto mapToRulesSetsDto() {
        return new RulesSetsDto(rulesSets.updateRules().stream()
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