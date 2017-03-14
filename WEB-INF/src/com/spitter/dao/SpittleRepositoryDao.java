package com.spitter.dao;

import java.util.List;

import com.spitter.orm.domain.Spittle;

/**
 * Repository interface with operations persistence.
 */
public interface SpittleRepositoryDao {

	long count();

	List<Spittle> findRecent();

	List<Spittle> findRecent(int count);

	Spittle findOne(long id);

	List<Spittle> findBySpitterId(long spitterId);

	List<Spittle> findAll();

	Spittle save(Spittle spittle);

	void delete(long id);
}
