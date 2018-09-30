package com.wawrze.restcheckers.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Scheduler {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedDelay = 1500000)
    public void getTasks() {
        restTemplate.getForObject("https://wawrze.herokuapp.com/v1/tasks", Object.class);
    }

}