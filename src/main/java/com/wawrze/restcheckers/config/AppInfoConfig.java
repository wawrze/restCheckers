package com.wawrze.restcheckers.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppInfoConfig {

    @Value("${info.app.owner.name.firstname}")
    private String ownerName;

    @Value("${info.app.owner.name.lastname}")
    private String ownerLastName;

    @Value("${info.app.owner.email}")
    private String ownerEmail;

    @Value("${info.app.owner.company}")
    private String ownerCompany;

}