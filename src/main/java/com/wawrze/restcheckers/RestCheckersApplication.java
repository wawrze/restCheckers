package com.wawrze.restcheckers;

import com.wawrze.restcheckers.gameplay.userInterface.GameEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestCheckersApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameEnvelope.class);

    public static void main(String[] args) {
        SpringApplication.run(RestCheckersApplication.class, args);
        LOGGER.info(" ###   #  #  ####   ###   #  #  ####  ###     ### ");
        LOGGER.info("#   #  #  #  #     #   #  # #   #     #  #   #    ");
        LOGGER.info("#      ####  ###   #      ##    ###   ###     ##  ");
        LOGGER.info("#   #  #  #  #     #   #  # #   #     # #   #   # ");
        LOGGER.info(" ###   #  #  ####   ###   #  #  ####  #  #   ###  ");
    }

}