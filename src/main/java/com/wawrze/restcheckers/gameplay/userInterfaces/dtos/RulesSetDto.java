package com.wawrze.restcheckers.gameplay.userInterfaces.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RulesSetDto {

    private boolean isVictoryConditionsReversed;
    private boolean isQueenRangeOne;
    private boolean isCaptureAny;
    private boolean isPawnMoveBackward;
    private boolean isPawnCaptureBackward;
    private boolean isQueenRangeOneAfterCapture;
    private String name;
    private String description;

}