package com.wawrze.restcheckers.gameplay;

import com.wawrze.restcheckers.board.Board;
import com.wawrze.restcheckers.figures.None;
import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.moves.Move;
import com.wawrze.restcheckers.moves.MoveValidator;
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

    public static boolean validateEndOfGame(Board board, int whiteQueenMoves, int blackQueenMoves, boolean player,
                                            RulesSet rulesSet) {
        draw = false;
        return validateFigures(board, rulesSet)
                || validateMovePossibility(board, player, rulesSet)
                || validateQueenMoves(whiteQueenMoves, blackQueenMoves);
    }

    private static boolean validateMovePossibility(Board board, boolean player, RulesSet rulesSet){
        boolean movePossible = false;
        for(int i = 1;i<9;i++) {
            if (!(board.getFigure('A', i) instanceof None) && board.getFigure('A', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'A', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('B', i) instanceof None) && board.getFigure('B', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'B', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('C', i) instanceof None) && board.getFigure('C', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'C', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('D', i) instanceof None) && board.getFigure('D', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'D', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('E', i) instanceof None) && board.getFigure('E', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'E', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('F', i) instanceof None) && board.getFigure('F', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'F', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('G', i) instanceof None) && board.getFigure('G', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'G', i, rulesSet);
                if(movePossible)
                    return false;
            }
            if (!(board.getFigure('H', i) instanceof None) && board.getFigure('H', i).getColor() == player){
                movePossible = validateFigureMovePossibility(board, 'H', i, rulesSet);
                if(movePossible)
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
        if(board.getFigure(row1,col1) instanceof Pawn || rulesSet.isQueenRangeOne())
            range = 3;
        char row2;
        int col2;
        Move move = null;
        for(int i = 1;i<range;i++) {
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
        for(int i = 1;i<range;i++) {
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
        for(int i = 1;i<range;i++) {
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
        for(int i = 1;i<range;i++) {
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
        for(int i = 1;i<9;i++) {
            if (!(board.getFigure('A', i) instanceof None)){
                if(board.getFigure('A', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('B', i) instanceof None)){
                if(board.getFigure('B', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('C', i) instanceof None)){
                if(board.getFigure('C', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('D', i) instanceof None)){
                if(board.getFigure('D', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('E', i) instanceof None)){
                if(board.getFigure('E', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('F', i) instanceof None)){
                if(board.getFigure('F', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('G', i) instanceof None)){
                if(board.getFigure('G', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
            if (!(board.getFigure('H', i) instanceof None)){
                if(board.getFigure('H', i).getColor())
                    blackFigures++;
                else
                    whiteFigures++;
            }
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