package com.wawrze.restcheckers.dtos.mappers;

import com.wawrze.restcheckers.domain.figures.Pawn;
import com.wawrze.restcheckers.domain.figures.Queen;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.dtos.DateTimeDto;
import com.wawrze.restcheckers.dtos.GameInfoDto;
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
                typeOfWin(game),
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
        if(dateTime != null)
            return new DateTimeDto(
                    dateTime.getYear(),
                    dateTime.getMonthValue(),
                    dateTime.getDayOfMonth(),
                    dateTime.getHour(),
                    dateTime.getMinute(),
                    dateTime.getSecond()
            );
        else
            return new DateTimeDto(0, 0, 0, 0, 0, 0);
    }

    public String typeOfWin(Game game) {
        if(!game.isFinished() || game.isDraw()) {
            return "";
        }
        else {
            if(game.getRulesSet().isVictoryConditionsReversed()) {
                if(game.isWinner()) {
                    if((countBlackPawns(game) + countBlackQueens(game)) == 0) //A
                        return "Black player lost all his figures.";
                    else
                        return "Black player cannot move.";
                }
                else {
                    if((countWhitePawns(game) + countWhiteQueens(game)) == 0) //B
                        return "White player lost all his figures.";
                    else
                        return "White player cannot move.";
                }
            }
            else {
                if(game.isWinner()) {
                    if((countWhitePawns(game) + countWhiteQueens(game)) == 0) //B
                        return "White player lost all his figures.";
                    else
                        return "White player cannot move.";
                }
                else {
                    if((countBlackPawns(game) + countBlackQueens(game)) == 0) //A
                        return "Black player lost all his figures.";
                    else
                        return "Black player cannot move.";
                }
            }
        }
    }

    public String mapDateTimeToString(LocalDateTime dateTime) {
        String s = "" + dateTime.getYear();
        s += ("-" + (dateTime.getMonthValue() < 10 ? "0" : "") + dateTime.getMonthValue());
        s += ("-" + (dateTime.getDayOfMonth() < 10 ? "0" : "") + dateTime.getDayOfMonth());
        s += (" " + (dateTime.getHour() < 10 ? "0" : "") + dateTime.getHour());
        s += (":" + (dateTime.getMinute() < 10 ? "0" : "") + dateTime.getMinute());
        s += (":" + (dateTime.getSecond() < 10 ? "0" : "") + dateTime.getSecond());
        return s;
    }

}