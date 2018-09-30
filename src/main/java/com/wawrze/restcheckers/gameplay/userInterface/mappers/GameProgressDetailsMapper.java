package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameProgressDetailsDto;
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
        return (int) game.getBoard().getRows().entrySet().stream()
                .flatMap(entry -> entry.getValue().getFigures().stream())
                .filter(figure -> figure instanceof Pawn)
                .filter(figure -> !figure.getColor())
                .count();
    }

    private int countBlackPawns(Game game) {
        return (int) game.getBoard().getRows().entrySet().stream()
                .flatMap(entry -> entry.getValue().getFigures().stream())
                .filter(figure -> figure instanceof Pawn)
                .filter(figure -> figure.getColor())
                .count();
    }

    private int countWhiteQueens(Game game) {
        return (int) game.getBoard().getRows().entrySet().stream()
                .flatMap(entry -> entry.getValue().getFigures().stream())
                .filter(figure -> figure instanceof Queen)
                .filter(figure -> !figure.getColor())
                .count();
    }

    private int countBlackQueens(Game game) {
        return (int) game.getBoard().getRows().entrySet().stream()
                .flatMap(entry -> entry.getValue().getFigures().stream())
                .filter(figure -> figure instanceof Queen)
                .filter(figure -> figure.getColor())
                .count();
    }

}