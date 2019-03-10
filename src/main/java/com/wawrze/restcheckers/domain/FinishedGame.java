package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.dtos.mappers.GameInfoMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "finished_games")
@NoArgsConstructor
@Setter
public class FinishedGame {

    private Long id;
    private String name;
    private String typeOfVictoryAndWinner;
    private int moves;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    public FinishedGame(Game game) {
        this.name = game.getName();
        this.typeOfVictoryAndWinner = getTypeOfVictoryAndWinner(game);
        this.moves = game.getMovesList().size();
        this.rulesSet = game.getRulesSet();
        this.isBlackAIPlayer = game.isBlackAIPlayer();
        this.isWhiteAIPlayer = game.isWhiteAIPlayer();
        this.startTime = game.getStartTime();
        this.finishTime = game.getLastAction();
    }

    private String getTypeOfVictoryAndWinner(Game game) {
        String result;
        if (game.isDraw()) {
            result = "draw";
        } else {
            if (game.isWinner())
                result = "black, ";
            else
                result = "white, ";
            String typeOfWin = new GameInfoMapper().typeOfWin(game);
            if (typeOfWin.contains("lost all his figures"))
                result += "lost figures";
            else
                result += "blocked";
        }
        return result;
    }

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

    @Column(name = "moves")
    public int getMoves() {
        return moves;
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

}