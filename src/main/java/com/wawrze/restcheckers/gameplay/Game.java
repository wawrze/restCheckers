package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.*;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "games")
@NoArgsConstructor
public class Game {

    private Long Id;
    private Board board;
    private List<String> moves;
    private Deque<String> inQueue = new ArrayDeque<>();
    private String gameStatus;
    private boolean activePlayer;
    private int whiteQueenMoves;
    private int blackQueenMoves;
    private boolean isFinished;
    private boolean isDraw;
    private boolean winner;
    private String name;
    private RulesSet rulesSet;
    private boolean isBlackAIPlayer;
    private boolean isWhiteAIPlayer;
    private LocalDateTime startTime;
    private LocalDateTime lastAction;

    public Game(String name, RulesSet rulesSet, boolean isBlackAIPlayer, boolean isWhiteAIPlayer) {
        moves = new LinkedList<>();
        activePlayer = false;
        whiteQueenMoves = 0;
        blackQueenMoves = 0;
        isFinished = false;
        isDraw = false;
        this.isBlackAIPlayer = isBlackAIPlayer;
        this.isWhiteAIPlayer = isWhiteAIPlayer;
        this.name = name;
        this.rulesSet = rulesSet;
        this.startTime = LocalDateTime.now();
        gameStatus = "Game started.";
        inQueue = new ArrayDeque<>();
        board = new Board.BoardBuilder().build().getNewBoard();
    }

    public void updateLastAction() {
        this.lastAction = LocalDateTime.now();
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return Id;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    public Board getBoard() {
        return board;
    }

    @Transient
    public List<String> getMovesList() {
        return moves;
    }

    @Column(name = "moves", length = 5000)
    public String getMoves() {
        String result = "";
        if(moves == null)
            return result;
        for(String s : moves)
            result += (s + ",");
        return result;
    }

    @Transient
    public Deque<String> getInQueue() {
        return inQueue;
    }

    @Column(name = "game_status")
    public String getGameStatus() {
        return gameStatus;
    }

    @Column(name = "active_player")
    public boolean isActivePlayer() {
        return activePlayer;
    }

    @Column(name = "white_queen_moves")
    public int getWhiteQueenMoves() {
        return whiteQueenMoves;
    }

    @Column(name = "black_queen_moves")
    public int getBlackQueenMoves() {
        return blackQueenMoves;
    }

    @Column(name = "finished")
    public boolean isFinished() {
        return isFinished;
    }

    @Column(name = "draw")
    public boolean isDraw() {
        return isDraw;
    }

    @Column(name = "winner")
    public boolean isWinner() {
        return winner;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rules_id")
    public RulesSet getRulesSet() {
        return rulesSet;
    }

    @Column(name = "black_AI_player")
    public boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    @Column(name = "white_AI_player")
    public boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

    @Column(name = "start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Column(name = "last_action")
    public LocalDateTime getLastAction() {
        return lastAction;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private void setId(Long id) {
        Id = id;
    }

    private void setMoves(String moves) {
        this.moves = new LinkedList<>();
        if (!moves.equals("")) {
            String[] movesList = moves.split(",");
            for (String s : movesList)
                this.moves.add(s);
        }
    }

    private void setInQueue(Deque<String> inQueue) {
        this.inQueue = new ArrayDeque<>();
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setActivePlayer(boolean activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setWhiteQueenMoves(int whiteQueenMoves) {
        this.whiteQueenMoves = whiteQueenMoves;
    }

    public void setBlackQueenMoves(int blackQueenMoves) {
        this.blackQueenMoves = blackQueenMoves;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRulesSet(RulesSet rulesSet) {
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

    private void setLastAction(LocalDateTime lastAction) {
        this.lastAction = lastAction;
    }

}