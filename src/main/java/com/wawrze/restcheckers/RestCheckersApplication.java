package com.wawrze.restcheckers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestCheckersApplication extends SpringBootServletInitializer {

    /*public static void main(String[] args) {
        SpringApplication.run(RestCheckersApplication.class, args);
    }*/

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestCheckersApplication.class);
    }

}