package com.wawrze.restcheckers.service.dbservices;

import com.wawrze.restcheckers.gameplay.RulesSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RulesSetDao extends CrudRepository<RulesSet, Long> {

    boolean existsByName(String name);

    @Override
    RulesSet save(RulesSet rulesSet);

    @Override
    List<RulesSet> findAll();

    RulesSet findByName(String name);

}