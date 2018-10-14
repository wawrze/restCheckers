package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.gameplay.moves.MoveValidator;
import exceptions.CaptureException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VictoryValidator {

    @Autowired
    private MoveValidator moveValidator;
    
    public boolean validateEndOfGame(Game game) {
        game.setDraw(false);
        return validateFigures(game)
                || validateMovePossibility(game)
                || validateQueenMoves(game);
    }

    private boolean validateMovePossibility(Game game){
        for(int i = 1;i<9;i++) {
            for(int j = 65;j < 73;j++) {
                if (
                        !(game.getBoard().getFigure((char) j, i).getFigureName().equals(Figure.NONE))
                                && game.getBoard().getFigure((char) j, i).getColor() == game.isActivePlayer()
                                && validateFigureMovePossibility(game, (char) j, i)
                        )
                    return false;
            }
        }
        if(game.getRulesSet().isVictoryConditionsReversed())
            game.setWinner(game.isActivePlayer());
        else
            game.setWinner(!game.isActivePlayer());
        return true;
    }

    private boolean validateFigureMovePossibility(Game game, char row1, int col1){
        int range = 8;
        if(game.getBoard().getFigure(row1,col1).getFigureName().equals(Figure.PAWN)
                || game.getRulesSet().isQueenRangeOne())
            range = 3;
        char row2;
        int col2;
        Move move = null;
        for(int i = 1;i < range;i++) {
            //left-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            }
            catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                moveValidator.validateMove(move, game.getBoard(), game.getBoard().getFigure(row1, col1).getColor(),
                        game.getRulesSet());
                return true;
            } catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                return true;
            }
        }
        for(int i = 1;i < range;i++) {
            //right-up
            row2 = (char) (((int) row1) - i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                moveValidator.validateMove(move, game.getBoard(), game.getBoard().getFigure(row1, col1).getColor(),
                        game.getRulesSet());
                return true;
            }
            catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                return true;
            }
        }
        for(int i = 1;i < range;i++) {
            //right-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 + i;
            try {
                move = new Move(row1, col1, row2, col2);
            } catch (IncorrectMoveFormat e) {
                break;
            }
            try {
                moveValidator.validateMove(move, game.getBoard(), game.getBoard().getFigure(row1, col1).getColor(),
                        game.getRulesSet());
                return true;
            } catch (IncorrectMoveException e) {}
            catch (CaptureException e) {
                return true;
            }
        }
        for(int i = 1;i < range;i++) {
            //left-down
            row2 = (char) (((int) row1) + i);
            col2 = col1 - i;
            try {
                move = new Move(row1, col1, row2, col2);
            }
            catch(IncorrectMoveFormat e){
                break;
            }
            try{
                moveValidator.validateMove(move, game.getBoard(), game.getBoard().getFigure(row1,col1).getColor(),
                        game.getRulesSet());
                return true;
            }
            catch(IncorrectMoveException e){}
            catch(CaptureException e){
                return true;
            }
        }
        return false;
    }

    private boolean validateQueenMoves(Game game){
        if(game.getWhiteQueenMoves() >= 15 && game.getBlackQueenMoves() >= 15){
            game.setDraw(true);
            return true;
        }
        return false;
    }

    private boolean validateFigures(Game game){
        int whiteFigures = 0;
        int blackFigures = 0;
        for(int i = 1;i < 9;i++)
            for(int j = 65;j < 73;j++)
                if (!(game.getBoard().getFigure((char) j, i).getFigureName().equals(Figure.NONE))){
                    if(game.getBoard().getFigure((char) j, i).getColor())
                        blackFigures++;
                    else
                        whiteFigures++;
                }
        if(whiteFigures == 0){
            if(game.getRulesSet().isVictoryConditionsReversed())
                game.setWinner(false);
            else
                game.setWinner(true);
            return true;
        }
        if(blackFigures == 0){
            if(game.getRulesSet().isVictoryConditionsReversed())
                game.setWinner(true);
            else
                game.setWinner(false);
            return true;
        }
        return false;
    }

}