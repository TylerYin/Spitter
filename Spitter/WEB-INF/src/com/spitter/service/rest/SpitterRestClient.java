package com.spitter.service.rest;

import java.util.List;

import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;

public interface SpitterRestClient {
	public List<Spittle> spittles();

	public Spitter findByUserName(String username);

	public Spitter saveSpitter(Spitter spitter);
}