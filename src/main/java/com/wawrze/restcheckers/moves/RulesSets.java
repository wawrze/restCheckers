package com.wawrze.restcheckers.moves;

import com.wawrze.restcheckers.gameplay.RulesSet;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class RulesSets {

    private List<RulesSet> rules;

    public RulesSets() {
        rules = new ArrayList<>();
        RulesSet rule = new RulesSet(false, false, false,
                false, true, false,
                "classic", "classic (brasilian) draughts");
        rules.add(rule);
        rule = new RulesSet(false, true, true,
                false, true, true,
                "english", "english draughts (checkers)");
        rules.add(rule);
        rule = new RulesSet(true, false, false,
                false, true, false,
                "poddavki", "standard rules, but reversed victory");
        rules.add(rule);
    }

}