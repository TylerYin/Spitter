package com.spitter.dao.hibernate.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spitter.dao.SpittleRepositoryDao;
import com.spitter.orm.domain.Spittle;

/**
 * @author Tyler Yin
 */
@Repository
public class HibernateSpittleRepositoryDaoImpl implements SpittleRepositoryDao {

    private SessionFactory sessionFactory;

    @Inject
    public HibernateSpittleRepositoryDaoImpl(SessionFactory sessionFactory) {
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
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Spittle> findRecent(int count) {
        return (List<Spittle>) spittleCriteria().setMaxResults(count).list();
    }

    @Override
    public Spittle findOne(long id) {
        return (Spittle) currentSession().get(Spittle.class, id);
    }

    @Override
    public Spittle save(Spittle spittle) {
        Serializable id = currentSession().save(spittle);
        return new Spittle((Long) id, spittle.getSpitter(), spittle.getMessage(), spittle.getPostedTime());
    }

    @Override
    public List<Spittle> findBySpitterId(long spitterId) {
        return spittleCriteria().add(Restrictions.eq("spitter.id", spitterId)).list();
    }

    @Override
    public void delete(long id) {
        currentSession().delete(findOne(id));
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Spittle> findAll() {
        return (List<Spittle>) spittleCriteria().list();
    }

    private Criteria spittleCriteria() {
        return currentSession().createCriteria(Spittle.class).addOrder(Order.desc("postedTime"));
    }

}
