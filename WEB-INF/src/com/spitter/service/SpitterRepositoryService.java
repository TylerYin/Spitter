package com.spitter.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.spitter.orm.domain.Spitter;

/**
 * @author Tyler Yin
 */
public interface SpitterRepositoryService {

    long count();

    @CachePut(value = "spittleCache", key = "#result.id")
    Spitter save(Spitter spitter);

    @Cacheable("spittleCache")
    Spitter findOne(long id);

    @Cacheable("spittleCache")
    Spitter findByUserName(String userName);

    @Cacheable("spittleCache")
    List<Spitter> findAll();

    @CacheEvict(value = "spittleCache", condition = "")
    void delete(long id);
}
