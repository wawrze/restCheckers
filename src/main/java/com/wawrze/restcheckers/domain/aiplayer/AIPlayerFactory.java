package com.wawrze.restcheckers.domain.aiplayer;

import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.gameplay.moves.AIPlayerExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AIPlayerFactory {

    @Autowired
    private AIPlayerExecutor aiPlayerExecutor;

    public AIPlayer newAIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves,
                                int blackQueenMoves) {
        AIPlayer aiPlayer = new AIPlayer(
                board,
                player,
                player,
                rulesSet,
                whiteQueenMoves,
                blackQueenMoves,
                1,
                new HashMap<>()
        );
        aiPlayerExecutor.getPossibleMoves(aiPlayer);
        aiPlayerExecutor.evaluateMoves(aiPlayer);
        return aiPlayer;
    }

    public AIPlayer newAIPlayer(Board board, boolean player, RulesSet rulesSet, int whiteQueenMoves,
                                int blackQueenMoves, char row, int col) {
        AIPlayer aiPlayer = new AIPlayer(
                board,
                player,
                player,
                rulesSet,
                whiteQueenMoves,
                blackQueenMoves,
                1,
                new HashMap<>()
        );
        aiPlayerExecutor.getPossibleMovesMultiCapture(aiPlayer, row, col);
        aiPlayerExecutor.evaluateMoves(aiPlayer);
        return aiPlayer;
    }

    public AIPlayer newAIPlayer(Board board, boolean aiPlayer, boolean activePlayer, RulesSet rulesSet,
                                 int whiteQueenMoves, int blackQueenMoves, int depth) {
        AIPlayer player = new AIPlayer(
                board,
                aiPlayer,
                activePlayer,
                rulesSet,
                whiteQueenMoves,
                blackQueenMoves,
                depth + 1,
                new HashMap<>()
        );
        aiPlayerExecutor.getPossibleMoves(player);
        aiPlayerExecutor.evaluateMoves(player);
        return player;
    }

    public AIPlayer newAIPlayer(Board board, boolean aiPlayer, boolean activePlayer, RulesSet rulesSet,
                                 int whiteQueenMoves, int blackQueenMoves, int depth, char row, int col) {
        AIPlayer player = new AIPlayer(
                board,
                aiPlayer,
                activePlayer,
                rulesSet,
                whiteQueenMoves,
                blackQueenMoves,
                depth + 1,
                new HashMap<>()
        );
        aiPlayerExecutor.getPossibleMovesMultiCapture(player, row, col);
        aiPlayerExecutor.evaluateMoves(player);
        return player;
    }

}