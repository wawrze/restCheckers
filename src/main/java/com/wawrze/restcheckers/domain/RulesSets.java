package com.wawrze.restcheckers.domain;

import com.wawrze.restcheckers.services.dbservices.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DependsOn("DBService")
public class RulesSets {

    @Autowired
    private DBService dbService;

    public List<RulesSet> updateRules() {
        RulesSet rule = new RulesSet(false, false, false,
                false, true, false,
                "classic", "classic (brasilian) draughts");
        dbService.saveRulesSet(rule);
        rule = new RulesSet(false, true, true,
                false, true, true,
                "english", "english draughts (checkers)");
        dbService.saveRulesSet(rule);
        rule = new RulesSet(true, false, false,
                false, true, false,
                "poddavki", "standard rules, but reversed victory");
        dbService.saveRulesSet(rule);
        return dbService.getAllRulesSets();
    }

}