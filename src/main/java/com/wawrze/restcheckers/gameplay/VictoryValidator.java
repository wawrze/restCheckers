package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import com.wawrze.restcheckers.domain.board.Board;
import com.wawrze.restcheckers.domain.figures.Figure;
import com.wawrze.restcheckers.domain.Move;
import com.wawrze.restcheckers.gameplay.moves.MoveValidator;
import exceptions.CaptureException;
import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;

public class VictoryValidator {

    private static boolean winner;
    private static boolean draw;

    public static boolean getWinner() {
        return winner;
    }

    public static boolean isDraw() {
        return draw;
    }

    public static boolean validateEndOfGame(Game game) {
        draw = false;
        return validateFigures(game.getBoard(), game.getRulesSet())
                || validateMovePossibility(game.getBoard(), game.isActivePlayer(), game.getRulesSet())
                || validateQueenMoves(game.getWhiteQueenMoves(), game.getBlackQueenMoves());
    }

    private static boolean validateMovePossibility(Board board, boolean player, RulesSet rulesSet){
        for(int i = 1;i<9;i++) {
            for(int j = 65;j < 73;j++) {
                if (!(board.getFigure((char) j, i).getFigureName().equals(Figure.NONE))
                        && board.getFigure((char) j, i).getColor() == player
                        && validateFigureMovePossibility(board, (char) j, i, rulesSet))
                    return false;
            }
        }
        if(rulesSet.isVictoryConditionsReversed())
            winner = player;
        else
            winner = !player;
        return true;
    }

    private static boolean validateFigureMovePossibility(Board board,char row1, int col1, RulesSet rulesSet){
        int range = 8;
        if(board.getFigure(row1,col1).getFigureName().equals(Figure.PAWN) || rulesSet.isQueenRangeOne())
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
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
                MoveValidator.validateMove(move, board, board.getFigure(row1, col1).getColor(), rulesSet);
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
                MoveValidator.validateMove(move,board,board.getFigure(row1,col1).getColor(), rulesSet);
                return true;
            }
            catch(IncorrectMoveException e){}
            catch(CaptureException e){
                return true;
            }
        }
        return false;
    }

    private static boolean validateQueenMoves(int whiteQueenMoves, int blackQueenMoves){
        if(whiteQueenMoves >= 15 && blackQueenMoves >= 15){
            draw = true;
            return true;
        }
        return false;
    }

    private static boolean validateFigures(Board board, RulesSet rulesSet){
        int whiteFigures = 0;
        int blackFigures = 0;
        for(int i = 1;i < 9;i++)
            for(int j = 65;j < 73;j++)
                if (!(board.getFigure((char) j, i).getFigureName().equals(Figure.NONE))){
                    if(board.getFigure((char) j, i).getColor())
                        blackFigures++;
                    else
                        whiteFigures++;
                }
        if(whiteFigures == 0){
            if(rulesSet.isVictoryConditionsReversed())
                winner = false;
            else
                winner = true;
            return true;
        }
        if(blackFigures == 0){
            if(rulesSet.isVictoryConditionsReversed())
                winner = true;
            else
                winner = false;
            return true;
        }
        return false;
    }

}