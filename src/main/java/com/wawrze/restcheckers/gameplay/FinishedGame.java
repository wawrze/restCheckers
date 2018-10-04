package com.wawrze.restcheckers.gameplay;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "finished_games")
@NoArgsConstructor
@AllArgsConstructor
public class FinishedGame {

    private Long id;
    private String name;
    private String typeOfVictoryAndWinner;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @Column(name = "id")
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "type_of_victory_and_winner")
    public String getTypeOfVictoryAndWinner() {
        return typeOfVictoryAndWinner;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rules_id")
    public RulesSet getRulesSet() {
        return rulesSet;
    }

    @Column(name = "black_ai_player")
    public boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    @Column(name = "white_ai_player")
    public boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

    @Column(name = "start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Column(name = "finish_time")
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setTypeOfVictoryAndWinner(String typeOfVictoryAndWinner) {
        this.typeOfVictoryAndWinner = typeOfVictoryAndWinner;
    }

    private void setRulesSet(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
    }

    private void setBlackAIPlayer(boolean blackAIPlayer) {
        isBlackAIPlayer = blackAIPlayer;
    }

    private void setWhiteAIPlayer(boolean whiteAIPlayer) {
        isWhiteAIPlayer = whiteAIPlayer;
    }

    private void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    private void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

}