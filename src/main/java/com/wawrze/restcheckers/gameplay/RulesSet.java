package com.wawrze.restcheckers.gameplay;

public final class RulesSet {

    private final boolean isVictoryConditionsReversed;
    private final boolean isQueenRangeOne;
    private final boolean isCaptureAny;
    private final boolean isPawnMoveBackward;
    private final boolean isPawnCaptureBackward;
    private final boolean isQueenRangeOneAfterCapture;
    private final String name;
    private final String description;

    public RulesSet(final boolean isVictoryConditionsReversed, final boolean isQueenRangeOne,
                    final boolean isCaptureAny, final boolean isPawnMoveBackward, final boolean isPawnCaptureBackward,
                    final boolean isQueenRangeOneAfterCapture, final String name, final String description) {
        this.isVictoryConditionsReversed = isVictoryConditionsReversed;
        this.isCaptureAny = isCaptureAny;
        this.isPawnMoveBackward = isPawnMoveBackward;
        this.isPawnCaptureBackward = isPawnCaptureBackward;
        this.isQueenRangeOne = isQueenRangeOne;
        if(isQueenRangeOne)
            this.isQueenRangeOneAfterCapture = true;
        else
            this.isQueenRangeOneAfterCapture = isQueenRangeOneAfterCapture;
        this.name = name;
        this.description = description;
    }

    public boolean isVictoryConditionsReversed() {
        return isVictoryConditionsReversed;
    }

    public boolean isQueenRangeOne() {
        return isQueenRangeOne;
    }

    public boolean isCaptureAny() {
        return isCaptureAny;
    }

    public boolean isPawnMoveBackward() {
        return isPawnMoveBackward;
    }

    public boolean isPawnCaptureBackward() {
        return isPawnCaptureBackward;
    }

    public boolean isQueenRangeOneAfterCapture() {
        return isQueenRangeOneAfterCapture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
