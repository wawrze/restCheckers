package com.wawrze.restcheckers.gameplay.moves;

import com.wawrze.restcheckers.domain.CapturesFinder;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.aiplayer.AIPlayer;
import com.wawrze.restcheckers.domain.aiplayer.AIPlayerFactory;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.gameplay.VictoryValidator;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class AIPlayerMoveEvaluator {

    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_DEPTH = 3;

    @Autowired
    private AIPlayerFactory aiPlayerFactory;

    @Autowired
    private VictoryValidator victoryValidator;

    @Autowired
    private CapturePossibilityValidator capturePossibilityValidator;

    @Autowired
    private MoveValidator moveValidator;

    public void evaluateMoves(AIPlayer aiPlayer) {
        Map<Move, Integer> moves = new HashMap<>(aiPlayer.getPossibleMoves());
        boolean capture;
        int value;
        for (Map.Entry<Move, Integer> entry : moves.entrySet()) {
            Board tmpBoard = new Board(aiPlayer.getBoard());
            capture = false;
            Game tmpGame;
            try {
                moveValidator.validateMove(entry.getKey(), tmpBoard, aiPlayer.isActivePlayer(), aiPlayer.getRulesSet());
                entry.getKey().makeMove(tmpBoard);
                if (tmpBoard.getFigure(
                        entry.getKey().getRow2(),
                        entry.getKey().getCol2()).getFigureName().equals(Figure.QUEEN)
                ) {
                    if (aiPlayer.isActivePlayer())
                        aiPlayer.increaseBlackQueenMoves();
                    else
                        aiPlayer.increaseWhiteQueenMoves();
                }
                tmpGame = getTmpGame(aiPlayer, tmpBoard);
                if (victoryValidator.validateEndOfGame(tmpGame)) {
                    value = evaluateWhenEndOfGame(aiPlayer, tmpGame);
                } else {
                    value = 1;
                }
            } catch (CaptureException e) {
                entry.getKey().makeCapture(tmpBoard, e.getRow(), e.getCol());
                try {
                    capturePossibilityValidator.validateCapturePossibilityForOneFigure(new CapturesFinder(
                            tmpBoard,
                            aiPlayer.isActivePlayer(),
                            aiPlayer.getRulesSet()
                    ), entry.getKey().getRow2(), entry.getKey().getCol2());
                    tmpGame = getTmpGame(aiPlayer, tmpBoard);
                    if (victoryValidator.validateEndOfGame(tmpGame)) {
                        value = evaluateWhenEndOfGame(aiPlayer, tmpGame);
                    } else {
                        value = 100;
                    }
                } catch (CapturePossibleException e1) {
                    value = 100;
                    capture = true;
                }
            } catch (IncorrectMoveException e) {
                value = 0;
            }
            if (aiPlayer.isActivePlayer() != aiPlayer.isPlayer())
                value *= -1;
            value += getFigureSetEvaluation(aiPlayer, tmpBoard);
            if (aiPlayer.getDepth() < MAX_DEPTH) {
                if (capture) {
                    AIPlayer nextMove = aiPlayerFactory.newAIPlayer(
                            tmpBoard,
                            aiPlayer.isPlayer(),
                            aiPlayer.isActivePlayer(),
                            aiPlayer.getRulesSet(),
                            aiPlayer.getWhiteQueenMoves(),
                            aiPlayer.getBlackQueenMoves(),
                            aiPlayer.getDepth(),
                            entry.getKey().getRow2(),
                            entry.getKey().getCol2()
                    );
                    value += getMovesMapValue(nextMove);
                } else {
                    AIPlayer nextMove = aiPlayerFactory.newAIPlayer(
                            tmpBoard,
                            aiPlayer.isPlayer(),
                            !aiPlayer.isActivePlayer(),
                            aiPlayer.getRulesSet(),
                            aiPlayer.getWhiteQueenMoves(),
                            aiPlayer.getBlackQueenMoves(),
                            aiPlayer.getDepth()
                    );
                    value += getMovesMapValue(nextMove);
                }
            }
            aiPlayer.getPossibleMoves().replace(entry.getKey(), value);
        }
    }

    private Game getTmpGame(AIPlayer aiPlayer, Board tmpBoard) {
        Game tmpGame = new Game(
                "tmpGame",
                aiPlayer.getRulesSet(),
                true,
                true
        );
        tmpGame.setBoard(tmpBoard);
        tmpGame.setWhiteQueenMoves(aiPlayer.getWhiteQueenMoves());
        tmpGame.setBlackQueenMoves(aiPlayer.getBlackQueenMoves());
        tmpGame.setActivePlayer(aiPlayer.isActivePlayer());
        return tmpGame;
    }

    private int getMovesMapValue(AIPlayer aiPlayer) {
        return aiPlayer.getPossibleMoves().entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    private int evaluateWhenEndOfGame(AIPlayer aiPlayer, Game game) {
        if (game.isDraw())
            return 0;
        else if (game.isWinner() == aiPlayer.isActivePlayer())
            return 10000;
        else
            return -10000;
    }

    public void getPossibleMovesMultiCapture(AIPlayer aiPlayer, char row, int col) {
        //noinspection EmptyFinallyBlock
        try {
            capturePossibilityValidator.validateCapturePossibilityForOneFigure(new CapturesFinder(
                    aiPlayer.getBoard(),
                    aiPlayer.isActivePlayer(),
                    aiPlayer.getRulesSet()
            ), row, col);
        } catch (CapturePossibleException e) {
            moveListFromCaptures(aiPlayer, e.getMessage());
        } finally {
        }
    }

    private void moveListFromCaptures(AIPlayer aiPlayer, String captureList) {
        String[] sArray = captureList.split(" ");
        for (String s : sArray)//noinspection Duplicates
        {
            String[] sA = s.split("-");
            char x1 = sA[0].charAt(0);
            int y1 = Character.getNumericValue(sA[0].charAt(1));
            char x2 = sA[1].charAt(0);
            int y2 = Character.getNumericValue(sA[1].charAt(1));
            try {
                Move move = new Move(x1, y1, x2, y2);
                aiPlayer.getPossibleMoves().put(move, 1);
            } catch (IncorrectMoveFormat ignored) {
            }
        }
    }

    public void getPossibleMoves(AIPlayer aiPlayer) {
        try {
            capturePossibilityValidator.validateCapturePossibility(new CapturesFinder(
                    aiPlayer.getBoard(),
                    aiPlayer.isActivePlayer(),
                    aiPlayer.getRulesSet()
            ));
            IntStream.iterate(1, i -> ++i)
                    .limit(8)
                    .forEach(i ->
                            IntStream.iterate(65, j -> ++j)
                                    .limit(8)
                                    .filter(j -> !(aiPlayer.getBoard().getFigure((char) j, i).getFigureName()
                                            .equals(Figure.NONE)))
                                    .filter(j -> aiPlayer.getBoard().getFigure((char) j, i).getColor()
                                            == aiPlayer.isActivePlayer())
                                    .forEach(j -> getFigureMovePossibility(aiPlayer, (char) j, i))
                    );
        } catch (CapturePossibleException e) {
            moveListFromCaptures(aiPlayer, e.getMessage());
        }
    }

    private int getFigureSetEvaluation(AIPlayer aiPlayer, Board board) {
        int value = 0;
        for (int i = 1; i < 9; i++)
            for (int j = 65; j < 73; j++)
                if (!(board.getFigure((char) j, i).getFigureName().equals(Figure.NONE))) {
                    if (board.getFigure((char) j, i).getFigureName().equals(Figure.QUEEN))
                        value += board.getFigure((char) j, i).getColor() ? -80 : 80;
                    else
                        value += board.getFigure((char) j, i).getColor() ? -(j - 64) : -(j - 73);
                }
        if (aiPlayer.isActivePlayer() != aiPlayer.isPlayer())
            value *= -1;
        return value;
    }

    @SuppressWarnings("Duplicates")
    private void getFigureMovePossibility(AIPlayer aiPlayer, char row, int col) {
        int range = 8;
        if (aiPlayer.getBoard().getFigure(row, col).getFigureName().equals(Figure.PAWN)
                || aiPlayer.getRulesSet().isQueenRangeOne())
            range = 3;
        char row2;
        int col2;
        for (int i = 1; i < range; i++) {
            //left-up
            row2 = (char) (((int) row) - i);
            col2 = col - i;
            if (isMoveInvalid(aiPlayer, row, col, row2, col2))
                break;
        }
        for (int i = 1; i < range; i++) {
            //right-up
            row2 = (char) (((int) row) - i);
            col2 = col + i;
            if (isMoveInvalid(aiPlayer, row, col, row2, col2))
                break;
        }
        for (int i = 1; i < range; i++) {
            //right-down
            row2 = (char) (((int) row) + i);
            col2 = col + i;
            if (isMoveInvalid(aiPlayer, row, col, row2, col2))
                break;
        }
        for (int i = 1; i < range; i++) {
            //left-down
            row2 = (char) (((int) row) + i);
            col2 = col - i;
            if (isMoveInvalid(aiPlayer, row, col, row2, col2))
                break;
        }
    }

    private boolean isMoveInvalid(AIPlayer aiPlayer, char row, int col, char row2, int col2) {
        Move move;
        try {
            move = new Move(row, col, row2, col2);
        } catch (IncorrectMoveFormat e) {
            return true;
        }
        try {
            moveValidator.validateMove(move, aiPlayer.getBoard(), aiPlayer.getBoard().getFigure(row, col).getColor(),
                    aiPlayer.getRulesSet());
            aiPlayer.getPossibleMoves().put(move, 0);
        } catch (IncorrectMoveException | CaptureException ignored) {
        }
        return false;
    }

    public String[] getAIMove(AIPlayer aiPlayer) {
        int max = -100000;
        int min = 100000;
        for (Map.Entry e : aiPlayer.getPossibleMoves().entrySet()) {
            if (aiPlayer.getRulesSet().isVictoryConditionsReversed()) {
                if (((int) e.getValue()) < min)
                    min = (int) e.getValue();
            } else {
                if (((int) e.getValue()) > max)
                    max = (int) e.getValue();
            }
        }
        List<Move> moves = new ArrayList<>();
        for (Map.Entry e : aiPlayer.getPossibleMoves().entrySet()) {
            if (aiPlayer.getRulesSet().isVictoryConditionsReversed()) {
                if (((int) e.getValue()) == min)
                    moves.add((Move) e.getKey());
            } else {
                if (((int) e.getValue()) == max)
                    moves.add((Move) e.getKey());
            }
        }
        Random r = new Random();
        Move bestMove = moves.get(r.nextInt(moves.size()));
        String[] s = new String[4];
        s[0] = "" + bestMove.getRow1();
        s[1] = "" + bestMove.getCol1();
        s[2] = "" + bestMove.getRow2();
        s[3] = "" + bestMove.getCol2();
        return s;
    }

}