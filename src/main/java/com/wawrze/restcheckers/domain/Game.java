package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.domain.board.Board;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "games")
@NoArgsConstructor
public class Game {

    private Long id;
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
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Transient
    public List<String> getMovesList() {
        return moves;
    }

    @Column(name = "moves", length = 5000)
    public String getMoves() {
        StringBuilder result = new StringBuilder();
        if (moves == null)
            return result.toString();
        for (String s : moves)
            result.append(s).append(",");
        return result.toString();
    }

    @SuppressWarnings("unused")
    private void setMoves(String moves) {
        this.moves = new LinkedList<>();
        if (!moves.equals("")) {
            String[] movesList = moves.split(",");
            Collections.addAll(this.moves, movesList);
        }
    }

    @Transient
    public Deque<String> getInQueue() {
        return inQueue;
    }

    @SuppressWarnings("unused")
    private void setInQueue(Deque<String> inQueue) {
        this.inQueue = new ArrayDeque<>();
    }

    @Column(name = "game_status")
    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Column(name = "active_player")
    public boolean isActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(boolean activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Column(name = "white_queen_moves")
    public int getWhiteQueenMoves() {
        return whiteQueenMoves;
    }

    public void setWhiteQueenMoves(int whiteQueenMoves) {
        this.whiteQueenMoves = whiteQueenMoves;
    }

    @Column(name = "black_queen_moves")
    public int getBlackQueenMoves() {
        return blackQueenMoves;
    }

    public void setBlackQueenMoves(int blackQueenMoves) {
        this.blackQueenMoves = blackQueenMoves;
    }

    @Column(name = "finished")
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Column(name = "draw")
    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    @Column(name = "winner")
    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rules_id")
    public RulesSet getRulesSet() {
        return rulesSet;
    }

    public void setRulesSet(RulesSet rulesSet) {
        this.rulesSet = rulesSet;
    }

    @Column(name = "black_AI_player")
    public boolean isBlackAIPlayer() {
        return isBlackAIPlayer;
    }

    @SuppressWarnings("unused")
    private void setBlackAIPlayer(boolean blackAIPlayer) {
        isBlackAIPlayer = blackAIPlayer;
    }

    @Column(name = "white_AI_player")
    public boolean isWhiteAIPlayer() {
        return isWhiteAIPlayer;
    }

    @SuppressWarnings("unused")
    private void setWhiteAIPlayer(boolean whiteAIPlayer) {
        isWhiteAIPlayer = whiteAIPlayer;
    }

    @Column(name = "start_time")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @SuppressWarnings("unused")
    private void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Column(name = "last_action")
    public LocalDateTime getLastAction() {
        return lastAction;
    }

    @SuppressWarnings("unused")
    private void setLastAction(LocalDateTime lastAction) {
        this.lastAction = lastAction;
    }

}