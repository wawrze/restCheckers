package com.wawrze.restcheckers.gameplay.userInterfaces.mappers;

import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.userInterfaces.dtos.GameProgressDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class GameProgressDetailsMapper {

    public GameProgressDetailsDto mapToGameProgressDetailsDto(Game game) {
        return new GameProgressDetailsDto(
                game.isFinished(),
                game.isDraw(),
                game.isWinner(),
                game.getMoves().size(),
                game.getBlackQueenMoves(),
                game.getWhiteQueenMoves(),
                countWhitePawns(game),
                countBlackPawns(game),
                countWhiteQueens(game),
                countBlackQueens(game)
        );
    }

    private int countWhitePawns(Game game) {
        int counter = 0;
        for(int i = 1;i < 9;i++) {
            for(int j = 65;j < 73;j++){
                if (!game.getBoard().getFigure((char) j, i).getColor()
                        && game.getBoard().getFigure((char) j, i) instanceof Pawn)
                    counter++;
            }
        }
        return counter;
    }

    private int countBlackPawns(Game game) {
        int counter = 0;
        for(int i = 1;i < 9;i++) {
            for(int j = 65;j < 73;j++){
                if (game.getBoard().getFigure((char) j, i).getColor()
                        && game.getBoard().getFigure((char) j, i) instanceof Pawn)
                    counter++;
            }
        }
        return counter;
    }

    private int countWhiteQueens(Game game) {
        int counter = 0;
        for(int i = 1;i < 9;i++) {
            for(int j = 65;j < 73;j++){
                if (!game.getBoard().getFigure((char) j, i).getColor()
                        && game.getBoard().getFigure((char) j, i) instanceof Queen)
                    counter++;
            }
        }
        return counter;
    }

    private int countBlackQueens(Game game) {
        int counter = 0;
        for(int i = 1;i < 9;i++) {
            for(int j = 65;j < 73;j++){
                if (game.getBoard().getFigure((char) j, i).getColor()
                        && game.getBoard().getFigure((char) j, i) instanceof Queen)
                    counter++;
            }
        }
        return counter;
    }

}