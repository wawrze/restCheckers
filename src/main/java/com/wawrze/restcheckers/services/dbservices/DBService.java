package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.domain.FinishedGame;
import com.wawrze.restcheckers.domain.Game;
import com.wawrze.restcheckers.domain.RulesSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Transactional
@Service
public class DBService {

    @Autowired
    GameDao gameDAO;

    @Autowired
    RulesSetDao rulesSetDao;

    @Autowired
    FinishedGameDao finishedGameDao;

    /////// RULES ////////////////////////////////////////////////

    public void saveRulesSet(RulesSet rulesSet) {
        if (!rulesSetDao.existsByName(rulesSet.getName()))
            rulesSetDao.save(rulesSet);
    }

    public List<RulesSet> getAllRulesSets() {
        return rulesSetDao.findAll();
    }

    public RulesSet getRulesSetByName(String name) {
        return rulesSetDao.findByName(name);
    }

    public void deleteRulesSet(Long id) {
        rulesSetDao.delete(id);
    }

    /////// GAME /////////////////////////////////////////////////

    public void saveGame(Game game) {
        gameDAO.save(game);
    }

    public Game getGameById(Long id) {
        return gameDAO.findById(id);
    }

    public List<Game> getAllGames() {
        return gameDAO.findAll();
    }

    public void deleteGame(Long id) {
        gameDAO.deleteById(id);
    }

    /////// FINISHED GAME ////////////////////////////////////////

    public void saveFinishedGame(Game game) {
        finishedGameDao.save(new FinishedGame(game));
    }

    public List<FinishedGame> getFinishedGames() {
        return finishedGameDao.findAll();
    }

    public void deleteFinishedGame(Long id) {
        finishedGameDao.delete(id);
    }

}