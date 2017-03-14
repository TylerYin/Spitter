package com.spitter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spitter.dao.SpittleRepositoryDao;
import com.spitter.orm.domain.Spittle;
import com.spitter.service.SpittleRepositoryService;

@Service
public class HibernateSpittleRepositoryServiceImpl implements SpittleRepositoryService {

	@Autowired
	private SpittleRepositoryDao spittleRepository;

	@Override
	public long count() {
		return spittleRepository.count();
	}

	@Override
	public List<Spittle> findRecent() {
		return spittleRepository.findRecent(10);
	}

	@Override
	public List<Spittle> findRecent(int count) {
		return spittleRepository.findRecent();
	}

	@Override
	public Spittle findOne(long id) {
		return spittleRepository.findOne(id);
	}

	@Override
	public Spittle save(Spittle spittle) {
		return spittleRepository.save(spittle);
	}

	@Override
	public List<Spittle> findBySpitterId(long spitterId) {
		return spittleRepository.findBySpitterId(spitterId);
	}

	@Override
	public void delete(long id) {
		spittleRepository.delete(id);
	}

	@Override
	public List<Spittle> findAll() {
		return spittleRepository.findAll();
	}
}
