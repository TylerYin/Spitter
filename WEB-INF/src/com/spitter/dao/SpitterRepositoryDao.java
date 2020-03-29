package com.spitter.dao;

import java.util.List;

import com.spitter.orm.domain.Spitter;

/**
 * @author Tyler Yin
 */
public interface SpitterRepositoryDao {

    long count();

    Spitter save(Spitter spitter);

    Spitter findOne(long id);

    Spitter findByUserName(String userName);

    List<Spitter> findAll();

    void delete(long id);
}
