package com.spitter.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spitter.dao.SpitterRepositoryDao;
import com.spitter.orm.domain.Spitter;
import com.spitter.security.encoder.MD5Encoder;

@Repository
public class HibernateSpitterRepositoryDaoImpl implements SpitterRepositoryDao {

	@Autowired
	public MD5Encoder md5Encoder;

	private SessionFactory sessionFactory;

	@Autowired
	public HibernateSpitterRepositoryDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long count() {
		return findAll().size();
	}

	@Override
	@Transactional
	public Spitter save(Spitter spitter) {
//		Spitter spitter = new Spitter();
//		spitter.setEmail(spitterForm.getEmail());
//		spitter.setFirstname(spitterForm.getFirstname());
//		spitter.setLastName(spitterForm.getLastName());
		spitter.setPassword(md5Encoder.encode(spitter.getPassword()));
//		spitter.setRole(spitterForm.getRole());
		//		spitter.setUsername(spitterForm.getUsername());
		
		Serializable id = currentSession().save(spitter);
		return new Spitter((Long) id, spitter.getUsername(), spitter.getPassword(), spitter.getFirstname(),
				spitter.getLastName(), spitter.getEmail(), spitter.getRole());
	}

	@Override
	public Spitter findOne(long id) {
		return (Spitter) currentSession().get(Spitter.class, id);
	}

	@Override
	@Transactional
	public Spitter findByUserName(String userName) {
		return (Spitter) currentSession().createCriteria(Spitter.class).add(Restrictions.eq("username", userName))
				.list().get(0);
	}

	@Override
	public List<Spitter> findAll() {
		return (List<Spitter>) currentSession().createCriteria(Spitter.class).list();
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}
}
