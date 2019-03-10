package com.wawrze.restcheckers.services;

import com.wawrze.restcheckers.config.AppInfoConfig;
import com.wawrze.restcheckers.dtos.mappers.GameInfoMapper;
import com.wawrze.restcheckers.services.dbservices.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class MailCreatorService {

    @Autowired
    AppInfoConfig appInfoConfig;

    @Autowired
    DBService dbService;

    @Autowired
    GameInfoMapper gameInfoMapper;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    String buildDailyEmail() {
        List<String[]> games = getGamesList();
        Context context = new Context();
        context.setVariable("message", "It's been " + games.size() + " games played last day.");
        context.setVariable("url", "https://wawrze.github.io/checkers/checkers.html");
        context.setVariable("button", "PLAY NOW");
        context.setVariable("games", games);
        context.setVariable("goodbye_message", "Till the next time, " + appInfoConfig.getOwnerName() + "!");
        context.setVariable("owner_name", appInfoConfig.getOwnerName());
        context.setVariable("owner_last_name", appInfoConfig.getOwnerLastName());
        context.setVariable("owner_email", appInfoConfig.getOwnerEmail());
        context.setVariable("owner_company", appInfoConfig.getOwnerCompany());
        return templateEngine.process("mail/daily-info-email", context);
    }

    private List<String[]> getGamesList() {
        List<String[]> games = new ArrayList<>();
        dbService.getFinishedGames().stream()
                .filter(game -> game.getFinishTime().plusHours(24L).isAfter(LocalDateTime.now()))
                .forEach(game -> {
                    String[] gameInfo = new String[8];
                    gameInfo[0] = game.getName();
                    gameInfo[1] = game.getRulesSet().getName();
                    gameInfo[2] = (game.isBlackAIPlayer() ? "computer" : "human");
                    gameInfo[3] = (game.isWhiteAIPlayer() ? "computer" : "human");
                    gameInfo[4] = game.getTypeOfVictoryAndWinner();
                    gameInfo[5] = String.valueOf(game.getMoves());
                    gameInfo[6] = gameInfoMapper.mapDateTimeToString(game.getStartTime());
                    gameInfo[7] = gameInfoMapper.mapDateTimeToString(game.getFinishTime());
                    games.add(gameInfo);
                });
        return games;
    }

}