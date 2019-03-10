package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface GameDao extends CrudRepository<Game, Long> {

    Game findById(Long id);

    void deleteById(Long id);

    @SuppressWarnings("unchecked")
    @Override
    Game save(Game game);

    @Override
    List<Game> findAll();

}