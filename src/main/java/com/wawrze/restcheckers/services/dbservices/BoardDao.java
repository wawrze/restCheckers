package com.wawrze.restcheckers.services.dbservices;

import com.wawrze.restcheckers.board.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BoardDao extends CrudRepository<Board, Long> {

    Board findById(Long id);

}
