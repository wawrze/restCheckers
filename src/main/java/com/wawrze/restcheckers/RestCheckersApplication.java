package com.wawrze.restcheckers;

import exceptions.IncorrectMoveException;
import exceptions.IncorrectMoveFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestCheckersApplication {

    public static void main(String[] args) throws IncorrectMoveFormat, IncorrectMoveException {
        SpringApplication.run(RestCheckersApplication.class, args);
    }

}