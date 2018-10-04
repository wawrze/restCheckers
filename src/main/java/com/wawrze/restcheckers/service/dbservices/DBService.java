package com.wawrze.restcheckers.service.dbservices;

import com.wawrze.restcheckers.gameplay.Game;
import com.wawrze.restcheckers.gameplay.RulesSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class DBService {

    @Autowired
    BoardDao boardDao;

    @Autowired
    GameDao gameDAO;

    @Autowired
    RulesSetDao rulesSetDao;

    /////// RULES ////////////////////////////////////////////////

    public void saveRulesSet(RulesSet rulesSet) {
        if(!rulesSetDao.existsByName(rulesSet.getName()))
            rulesSetDao.save(rulesSet);
    }

    public List<RulesSet> getAllRulesSets() {
        return rulesSetDao.findAll();
    }

    public RulesSet getRulesSetByName(String name) {
        return rulesSetDao.findByName(name);
    }

    /////// GAME ////////////////////////////////////////////////

    public boolean isGameExisting(String name) {
        return gameDAO.existsByName(name);
    }

    public Game getGameByName(String name) {
        return gameDAO.getByName(name);
    }

    public void saveGame(Game game) {
        gameDAO.save(game);
    }

    public Game getGameById(Long id) {
        return  gameDAO.findById(id);
    }

    public List<Game> getAllGames() {
        return gameDAO.findAll();
    }

    public void deleteGame(Long id) {
        gameDAO.deleteById(id);
    }

}