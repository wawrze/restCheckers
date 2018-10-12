package com.wawrze.restcheckers.domain.aiplayer;

import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.gameplay.moves.AIPlayerMoveEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AIPlayerFactory {

    @Autowired
    private AIPlayerMoveEvaluator aiPlayerMoveEvaluator;

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
        aiPlayerMoveEvaluator.getPossibleMoves(aiPlayer);
        aiPlayerMoveEvaluator.evaluateMoves(aiPlayer);
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
        aiPlayerMoveEvaluator.getPossibleMovesMultiCapture(aiPlayer, row, col);
        aiPlayerMoveEvaluator.evaluateMoves(aiPlayer);
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
        aiPlayerMoveEvaluator.getPossibleMoves(player);
        aiPlayerMoveEvaluator.evaluateMoves(player);
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
        aiPlayerMoveEvaluator.getPossibleMovesMultiCapture(player, row, col);
        aiPlayerMoveEvaluator.evaluateMoves(player);
        return player;
    }

}