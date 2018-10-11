package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.figures.FigureFactory;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.gameplay.moves.AIPlayerExecutor;
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
    private AIPlayerExecutor aiPlayerExecutor;

    private final static Logger LOGGER = LoggerFactory.getLogger(GameExecutor.class);

    public void play(Game game) {
        boolean b;
        do {
            boolean isFinished = VictoryValidator.validateEndOfGame(game);
            if(isFinished) {
                game.setFinished(true);
                game.setDraw(VictoryValidator.isDraw());
                game.setWinner(VictoryValidator.getWinner());
                break;
            }
            b = this.waitForMove(game);
        } while (b);
    }

    private boolean waitForMove(Game game) {
        String captures = "";
        try {
            (new CapturePossibilityValidator(
                    game.getBoard(),
                    game.isActivePlayer(),
                    game.getRulesSet()
            )).validateCapturePossibility();
        }
        catch(CapturePossibleException e){
            captures = e.getMessage();
            restUI.printCapture(game, captures);
        }
        String[] s;
        if((game.isBlackAIPlayer() && game.isActivePlayer()) || (game.isWhiteAIPlayer() && !game.isActivePlayer())) {
            s = aiPlayerExecutor.getAIMove(aiPlayerExecutor.newAIPlayer(
                    game.getBoard(),
                    game.isActivePlayer(),
                    game.getRulesSet(),
                    game.getWhiteQueenMoves(),
                    game.getBlackQueenMoves()
            ));
            if(game.isBlackAIPlayer() && game.isWhiteAIPlayer()) {
                String[] t;
                do {
                    t = restUI.getMoveOrOption(game, captures);
                }
                while(t == null);
            }
        }
        else {
            s = restUI.getMoveOrOption(game, captures);
        }
        if(s == null)
            return true;
        else if(s.length == 1) {
            return !s[0].equals("x");
        }
        else {
            try {
                this.makeMove(game, s);
                return true;
            }
            catch(IncorrectMoveFormat e) {
                restUI.printIncorrectMoveFormat(game);
                return true;
            }
        }
    }

    private void makeMove(Game game, String[] s) throws IncorrectMoveFormat {
        char x1 = s[0].charAt(0);
        int y1 = Character.getNumericValue(s[1].charAt(0));
        char x2 = s[2].charAt(0);
        int y2 = Character.getNumericValue(s[3].charAt(0));
        Move move = new Move(x1, y1, x2, y2);
        try {
            MoveValidator.validateMove(move, game.getBoard(), game.isActivePlayer(), game.getRulesSet());
            game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
            move.makeMove(game.getBoard());
            if(game.getBoard().getFigure(move.getRow2(),move.getCol2()).getFigureName().equals(Figure.QUEEN)){
                if(game.isActivePlayer())
                    game.setBlackQueenMoves(game.getBlackQueenMoves() + 1);
                else
                    game.setWhiteQueenMoves(game.getWhiteQueenMoves() + 1);
            }
            else{
                if(game.isActivePlayer())
                    game.setBlackQueenMoves(0);
                else
                    game.setWhiteQueenMoves(0);
            }
            game.setActivePlayer(!game.isActivePlayer());
            restUI.printMoveDone(game);
        }
        catch (CaptureException e) {
            game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
            move.makeCapture(game.getBoard(), e.getRow(), e.getCol());
            try {
                multiCapture(game, move);
            }
            catch(IncorrectMoveException e1) {}
            restUI.printCaptureDone(game);
            if(game.isActivePlayer())
                game.setBlackQueenMoves(0);
            else
                game.setWhiteQueenMoves(0);
            game.setActivePlayer(!game.isActivePlayer());
        }
        catch (IncorrectMoveException e) {
            restUI.printIncorrectMove(game, e.getMessage());
        }
        finally {
            if((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                    && game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'H')
                game.getBoard().setFigure('H', move.getCol2(), figureFactory.getNewFigure(true, Figure.QUEEN));
            if((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                    && !game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                    && (move.getRow2()) == 'A')
                game.getBoard().setFigure('A', move.getCol2(), figureFactory.getNewFigure(false, Figure.QUEEN));
        }
    }

    private void multiCapture(Game game, Move move) throws IncorrectMoveFormat, IncorrectMoveException {
        do {
            try{
                (new CapturePossibilityValidator(game.getBoard(), game.isActivePlayer(), game.getRulesSet()))
                        .validateCapturePossibilityForOneFigure(move.getRow2(),move.getCol2());
                break;
            }
            catch(CapturePossibleException e){
                restUI.printMultiCapture(game, e.getMessage());
                String[] s;
                if((game.isBlackAIPlayer() && game.isActivePlayer())
                        || (game.isWhiteAIPlayer() && !game.isActivePlayer())) {
                    s = aiPlayerExecutor.getAIMove(aiPlayerExecutor.newAIPlayer(
                            game.getBoard(),
                            game.isActivePlayer(),
                            game.getRulesSet(),
                            game.getWhiteQueenMoves(),
                            game.getBlackQueenMoves(),
                            move.getRow2(),
                            move.getCol2())
                    );
                }
                else {
                    s = restUI.getMoveOrOption(game, e.getMessage());
                }
                if(s == null)
                    continue;
                else {
                    char x1 = s[0].charAt(0);
                    int y1 = Character.getNumericValue(s[1].charAt(0));
                    char x2 = s[2].charAt(0);
                    int y2 = Character.getNumericValue(s[3].charAt(0));
                    move = new Move(x1, y1, x2, y2);
                    try{
                        MoveValidator.validateMove(move, game.getBoard(), game.isActivePlayer(), game.getRulesSet());
                    }
                    catch(CaptureException e1){
                        game.getMovesList().add((game.isActivePlayer() ? "black: " : "white: ") + move);
                        move.makeCapture(game.getBoard(), e1.getRow(), e1.getCol());
                    }
                    finally {}
                }
                continue;
            }
        } while(true);
        if((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'H')
            game.getBoard().setFigure('H', move.getCol2(), figureFactory.getNewFigure(true, Figure.QUEEN));
        if((game.getBoard().getFigure(move.getRow2(), move.getCol2()).getFigureName().equals(Figure.PAWN))
                && !game.getBoard().getFigure(move.getRow2(), move.getCol2()).getColor()
                && (move.getRow2()) == 'A')
            game.getBoard().setFigure('A', move.getCol2(), figureFactory.getNewFigure(false, Figure.QUEEN));
    }
    
}