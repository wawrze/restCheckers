package com.wawrze.restcheckers;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestCheckersApplication {

    public static void main(String[] args) {
        Game game = new Game(
                "Rest game",
                new RulesSet(
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        "classic",
                        "classic (brasilian) draughts"
                ),
                true,
                false
        );
        SpringApplication.run(RestCheckersApplication.class, args);
    }
}
