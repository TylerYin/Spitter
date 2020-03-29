package com.spitter.service.rest;

import java.util.List;

import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;

/**
 * @author Tyler Yin
 */
public interface SpitterRestClient {
    List<Spittle> spittles();

    Spitter findByUserName(String username);

    Spitter saveSpitter(Spitter spitter);
}