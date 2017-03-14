package com.spitter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spitter.dao.SpitterRepositoryDao;
import com.spitter.orm.domain.Spitter;
import com.spitter.service.SpitterRepositoryService;

@Service
public class HibernateSpitterRepositoryServiceImpl implements SpitterRepositoryService {

	@Autowired
	private SpitterRepositoryDao spitterRepository;

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	public Spitter save(Spitter spitter) {
		return spitterRepository.save(spitter);
	}

	@Override
	public Spitter findOne(long id) {
		return spitterRepository.findOne(id);
	}

	@Override
	public Spitter findByUserName(String userName) {
		return spitterRepository.findByUserName(userName);
	}

	@Override
	public List<Spitter> findAll() {
		return spitterRepository.findAll();
	}

	@Override
	public void delete(long id) {
		spitterRepository.delete(id);
	}
}
