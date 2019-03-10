package com.wawrze.restcheckers.services;

import com.wawrze.restcheckers.config.AppInfoConfig;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.gameplay.GameEnvelope;
import com.wawrze.restcheckers.services.dbservices.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class Scheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    GameEnvelope gameEnvelope;

    @Autowired
    DBService dbService;

    @Autowired
    EmailService emailService;

    @Autowired
    MailCreatorService mailCreatorService;

    @Autowired
    AppInfoConfig appInfoConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(fixedDelay = 1500000)
    private void getTasks() {
        restTemplate.getForObject("https://wawrze.herokuapp.com/v1/tasks", Object.class);
        LOGGER.info("Sending request to Task application...");
    }

    @Scheduled(fixedDelay = 300000)
    private void archiveGamesToDB() {
        LOGGER.info("Starting to archive non-active games...");
        Map<Long, Game> games = new HashMap<>(gameEnvelope.getGames());
        games.values().stream()
                .filter(game -> game.getLastAction() != null)
                .filter(game -> (game.getLastAction().plusMinutes(5L).isBefore(LocalDateTime.now())))
                .forEach(game -> {
                    dbService.saveGame(game);
                    gameEnvelope.getGames().remove(game.getId());
                    LOGGER.info("Game \"" + game.getName() + "\" moved to DB.");
                });
        LOGGER.info("Archiving finished.");
    }

    @Scheduled(fixedDelay = 3600000)
    private void archiveGamesInDB() {
        LOGGER.info("Starting to clean non-active games from DB...");
        List<Game> games = dbService.getAllGames();
        games.stream()
                .filter(game -> game.getLastAction() != null)
                .filter(game -> game.getLastAction().plusHours(1L).isBefore(LocalDateTime.now()))
                .forEach(game -> {
                    dbService.deleteGame(game.getId());
                    LOGGER.info("Game \"" + game.getName() + "\" deleted from DB.");
                });
        games.stream()
                .filter(Game::isFinished)
                .forEach(game -> {
                    dbService.saveFinishedGame(game);
                    dbService.deleteGame(game.getId());
                    LOGGER.info("Game \"" + game.getName() + "\" moved to finished games.");
                });
        LOGGER.info("Cleaning finished.");
    }

    @Scheduled(cron = "0 0 10 * * *")
    private void sentDailyEmail() {
        String to = appInfoConfig.getOwnerEmail();
        String subject = "RestCheckers - daily info";
        emailService.send(to, subject, mailCreatorService.buildDailyEmail());
        LOGGER.info("Daily email sent.");
    }

}