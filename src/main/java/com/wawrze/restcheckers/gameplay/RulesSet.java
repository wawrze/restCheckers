package com.wawrze.restcheckers.gameplay;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rules")
@NoArgsConstructor
public final class RulesSet {

    private Long id;
    private boolean isVictoryConditionsReversed;
    private boolean isQueenRangeOne;
    private boolean isCaptureAny;
    private boolean isPawnMoveBackward;
    private boolean isPawnCaptureBackward;
    private boolean isQueenRangeOneAfterCapture;
    private String name;
    private String description;

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

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "victory_conditions_reversed")
    public boolean isVictoryConditionsReversed() {
        return isVictoryConditionsReversed;
    }

    @Column(name = "queen_range_one")
    public boolean isQueenRangeOne() {
        return isQueenRangeOne;
    }

    @Column(name = "capture_any")
    public boolean isCaptureAny() {
        return isCaptureAny;
    }

    @Column(name = "pawn_move_backward")
    public boolean isPawnMoveBackward() {
        return isPawnMoveBackward;
    }

    @Column(name = "pawn_capture_backward")
    public boolean isPawnCaptureBackward() {
        return isPawnCaptureBackward;
    }

    @Column(name = "queen_range_one_after_capture")
    public boolean isQueenRangeOneAfterCapture() {
        return isQueenRangeOneAfterCapture;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setVictoryConditionsReversed(boolean victoryConditionsReversed) {
        isVictoryConditionsReversed = victoryConditionsReversed;
    }

    private void setQueenRangeOne(boolean queenRangeOne) {
        isQueenRangeOne = queenRangeOne;
    }

    private void setCaptureAny(boolean captureAny) {
        isCaptureAny = captureAny;
    }

    private void setPawnMoveBackward(boolean pawnMoveBackward) {
        isPawnMoveBackward = pawnMoveBackward;
    }

    private void setPawnCaptureBackward(boolean pawnCaptureBackward) {
        isPawnCaptureBackward = pawnCaptureBackward;
    }

    private void setQueenRangeOneAfterCapture(boolean queenRangeOneAfterCapture) {
        isQueenRangeOneAfterCapture = queenRangeOneAfterCapture;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDescription(String description) {
        this.description = description;
    }
}
