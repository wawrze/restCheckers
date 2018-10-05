package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.gameplay.FinishedGame;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface FinishedGameDao extends CrudRepository<FinishedGame, Long> {

    @Override
    FinishedGame save(FinishedGame finishedGame);

    @Override
    List<FinishedGame> findAll();

    @Override
    void delete(Long id);

}