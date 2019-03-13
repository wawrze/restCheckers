package com.wawrze.restcheckers.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rules")
@NoArgsConstructor
public class RulesSet {

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
        if (isQueenRangeOne)
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

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    @Column(name = "victory_conditions_reversed")
    public boolean isVictoryConditionsReversed() {
        return isVictoryConditionsReversed;
    }

    @SuppressWarnings("unused")
    private void setVictoryConditionsReversed(boolean victoryConditionsReversed) {
        isVictoryConditionsReversed = victoryConditionsReversed;
    }

    @Column(name = "queen_range_one")
    public boolean isQueenRangeOne() {
        return isQueenRangeOne;
    }

    @SuppressWarnings("unused")
    private void setQueenRangeOne(boolean queenRangeOne) {
        isQueenRangeOne = queenRangeOne;
    }

    @Column(name = "capture_any")
    public boolean isCaptureAny() {
        return isCaptureAny;
    }

    @SuppressWarnings("unused")
    private void setCaptureAny(boolean captureAny) {
        isCaptureAny = captureAny;
    }

    @Column(name = "pawn_move_backward")
    public boolean isPawnMoveBackward() {
        return isPawnMoveBackward;
    }

    @SuppressWarnings("unused")
    private void setPawnMoveBackward(boolean pawnMoveBackward) {
        isPawnMoveBackward = pawnMoveBackward;
    }

    @Column(name = "pawn_capture_backward")
    public boolean isPawnCaptureBackward() {
        return isPawnCaptureBackward;
    }

    @SuppressWarnings("unused")
    private void setPawnCaptureBackward(boolean pawnCaptureBackward) {
        isPawnCaptureBackward = pawnCaptureBackward;
    }

    @Column(name = "queen_range_one_after_capture")
    public boolean isQueenRangeOneAfterCapture() {
        return isQueenRangeOneAfterCapture;
    }

    @SuppressWarnings("unused")
    private void setQueenRangeOneAfterCapture(boolean queenRangeOneAfterCapture) {
        isQueenRangeOneAfterCapture = queenRangeOneAfterCapture;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    private void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    private void setDescription(String description) {
        this.description = description;
    }
}
