package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.domain.RulesSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RulesSetDao extends CrudRepository<RulesSet, Long> {

    boolean existsByName(String name);

    @SuppressWarnings("unchecked")
    @Override
    RulesSet save(RulesSet rulesSet);

    @Override
    List<RulesSet> findAll();

    @Override
    void delete(Long id);

    RulesSet findByName(String name);

}