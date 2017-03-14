package com.spitter.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.spitter.orm.domain.Spittle;

/**
 * Repository interface with operations persistence.
 */
public interface SpittleRepositoryService {

	long count();

	List<Spittle> findRecent();

	List<Spittle> findRecent(int count);

	Spittle findOne(long id);

	List<Spittle> findBySpitterId(long spitterId);

	@Cacheable("spitterCache")
	List<Spittle> findAll();

	Spittle save(Spittle spittle);

	void delete(long id);
}
