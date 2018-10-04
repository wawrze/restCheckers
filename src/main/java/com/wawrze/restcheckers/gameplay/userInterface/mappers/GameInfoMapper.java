package com.wawrze.restcheckers.gameplay.userInterface.mappers;

import com.wawrze.restcheckers.figures.Pawn;
import com.wawrze.restcheckers.figures.Queen;
import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.DateTimeDto;
import com.wawrze.restcheckers.gameplay.userInterface.dtos.GameInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GameInfoMapper {

    @Autowired
    private BoardMapper boardMapper;

    public GameInfoDto mapToGameProgressDetailsDto(Game game) {
        String movesHistory;
        List<String> moves = game.getMovesList();
        if(moves.isEmpty()) {
            movesHistory = "";
        }
        else {
            int i = moves.size() - 1;
            movesHistory = (i + 1) + ". " + moves.get(i);
            for (i--; i >= 0; i--) {
                if (moves.get(i).charAt(0) == moves.get(i + 1).charAt(0)) {
                    movesHistory += ("\n" + (i + 1) + ". " + moves.get(i));
                } else {
                    movesHistory += ("\n\n" + (i + 1) + ". " + moves.get(i));
                }
            }
        }
        return new GameInfoDto(
                game.getName(),
                game.getRulesSet().getName(),
                game.getGameStatus(),
                movesHistory,
                boardMapper.mapToBoardDto(game.getBoard()),
                game.isActivePlayer(),
                game.isWhiteAIPlayer(),
                game.isBlackAIPlayer(),
                game.isFinished(),
                game.isDraw(),
                game.isWinner(),
                game.getMovesList().size(),
                game.getBlackQueenMoves(),
                game.getWhiteQueenMoves(),
                countWhitePawns(game),
                countBlackPawns(game),
                countWhiteQueens(game),
                countBlackQueens(game),
                mapToDateTimeDto(game.getStartTime()),
                mapToDateTimeDto(game.getLastAction())
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

    private DateTimeDto mapToDateTimeDto(LocalDateTime dateTime) {
        return new DateTimeDto(
                dateTime.getYear(),
                dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond()
        );
    }

}