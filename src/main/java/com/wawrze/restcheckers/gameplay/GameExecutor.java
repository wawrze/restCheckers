package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.CapturesFinder;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.domain.aiplayer.AIPlayerFactory;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;
import com.wawrze.restcheckers.gameplay.moves.AIPlayerMoveEvaluator;
import com.wawrze.restcheckers.gameplay.moves.CapturePossibilityValidator;
import com.wawrze.restcheckers.gameplay.moves.MoveValidator;
import exceptions.CaptureException;
import exceptions.CapturePossibleException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameExecutor {

    @Autowired
    private FigureFactory figureFactory;

    @Autowired
    private RestUI restUI;

    @Autowired
    private AIPlayerMoveEvaluator aiPlayerMoveEvaluator;

    @Autowired
    private AIPlayerFactory aiPlayerFactory;

    @Autowired
    private VictoryValidator victoryValidator;

    @Autowired
    private CapturePossibilityValidator capturePossibilityValidator;

    @Autowired
    private MoveValidator moveValidator;

    private final static Logger LOGGER = LoggerFactory.getLogger(GameExecutor.class);

    void play(Game game) {
        boolean b;
        do {
            boolean isFinished = victoryValidator.validateEndOfGame(game);
            if (isFinished) {
                game.setFinished(true);
                break;
            }
            b = this.waitForMove(game);
        } while (b);
    }

    private boolean waitForMove(Game game) {
        String captures = "";
        try {
            capturePossibilityValidator.validateCapturePossibility(new CapturesFinder(
                    game.getBoard(),
                    game.isActivePlayer(),
                    game.getRulesSet()
            ));
        } catch (CapturePossibleException e) {
            captures = e.getMessage();
            restUI.printCapture(game, captures);
        }
        String[] s;
        if ((game.isBlackAIPlayer() && game.isActivePlayer()) || (game.isWhiteAIPlayer() && !game.isActivePlayer())) {
            s = aiPlayerMoveEvaluator.getAIMove(aiPlayerFactory.newAIPlayer(
                    game.getBoard(),
                    game.isActivePlayer(),
                    game.getRulesSet(),
                    game.getWhiteQueenMoves(),
                    game.getBlackQueenMoves()
            ));
            if (game.isBlackAIPlayer() && game.isWhiteAIPlayer()) {
                String[] t;
                do {
                    t = restUI.getMoveOrOption(game, captures);
                }
                while (t.length == 0);
            }
        } else {
            s = restUI.getMoveOrOption(game, captures);
        }
        if (s.length == 0)
            return true;
        else if (s.length == 1) {
            return !s[0].equals("x");
        } else {
            try {
                this.makeMove(game, s);
                return true;
            } catch (IncorrectMoveFormat e) {
                restUI.printIncorrectMoveFormat(game);
                return true;
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void makeMove(Game game, String[] s) throws IncorrectMoveFormat {
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        Move move = new Move(x1, y1, x2, y2);
        try {
            moveValidator.validateMove(move, game.getBoard(), game.isActivePlayer(), game.getRulesSet());
            game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
            move.makeMove(game.getBoard());
            if (game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.QUEEN)) {
                if (game.isActivePlayer())
                    game.setBlackQueenMoves(game.getBlackQueenMoves() + 1);
                else
                    game.setWhiteQueenMoves(game.getWhiteQueenMoves() + 1);
            } else {
                if (game.isActivePlayer())
                    game.setBlackQueenMoves(0);
                else
                    game.setWhiteQueenMoves(0);
            }
            game.setActivePlayer(!game.isActivePlayer());
            restUI.printMoveDone(game);
        } catch (CaptureException e) {
            game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
            move.makeCapture(game.getBoard(), e.getRow(), e.getCol());
            try {
                multiCapture(game, move);
            } catch (IncorrectMoveException ignored) {
            }
            restUI.printCaptureDone(game);
            if (game.isActivePlayer())
                game.setBlackQueenMoves(0);
            else
                game.setWhiteQueenMoves(0);
            game.setActivePlayer(!game.isActivePlayer());
        } catch (IncorrectMoveException e) {
            restUI.printIncorrectMove(game, e.getMessage());
        } finally {
            moveFigure(game, move);
        }
    }

    @SuppressWarnings("Duplicates")
    private void multiCapture(Game game, Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        do {
            try {

                capturePossibilityValidator.validateCapturePossibilityForOneFigure(new CapturesFinder(
                        game.getBoard(),
                        game.isActivePlayer(),
                        game.getRulesSet()
                ), move.getRow2(), move.getCol2());
                break;
            } catch (CapturePossibleException e) {
                restUI.printMultiCapture(game, e.getMessage());
                String[] s;
                if ((game.isBlackAIPlayer() && game.isActivePlayer())
                        || (game.isWhiteAIPlayer() && !game.isActivePlayer())) {
                    s = aiPlayerMoveEvaluator.getAIMove(aiPlayerFactory.newAIPlayer(
                            game.getBoard(),
                            game.isActivePlayer(),
                            game.getRulesSet(),
                            game.getWhiteQueenMoves(),
                            game.getBlackQueenMoves(),
                            move.getRow2(),
                            move.getCol2())
                    );
                } else {
                    s = restUI.getMoveOrOption(game, e.getMessage());
                }
                if (s.length != 0) {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    move = new Move(x1, y1, x2, y2);
                    //noinspection EmptyFinallyBlock
                    try {
                        moveValidator.validateMove(move, game.getBoard(), game.isActivePlayer(), game.getRulesSet());
                    } catch (CaptureException e1) {
                        game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
                        move.makeCapture(game.getBoard(), e1.getRow(), e1.getCol());
                    } finally {
                    }
                }
            }
        } while (true);
        moveFigure(game, move);
    }

    private void moveFigure(Game game, Move move) {
        if ((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            game.getBoard().setFigure('H', move.getCol2(), figureFactory.getNewFigure(true, Figure.QUEEN));
        if ((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && !game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            game.getBoard().setFigure('A', move.getCol2(), figureFactory.getNewFigure(false, Figure.QUEEN));
    }

}