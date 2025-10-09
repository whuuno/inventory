package com.whuuno.inventory.dao;

import com.whuuno.inventory.model.Inventory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryDAOImpl implements InventoryDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Inventory save(Inventory inventory) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(inventory);
        return inventory;
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Inventory inventory = session.get(Inventory.class, id);
        return Optional.ofNullable(inventory);
    }

    @Override
    public List<Inventory> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Inventory> query = session.createQuery("FROM Inventory", Inventory.class);
        return query.getResultList();
    }

    @Override
    public Inventory update(Inventory inventory) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(inventory);
        return inventory;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Inventory inventory = session.get(Inventory.class, id);
        if(inventory != null) {
            session.remove(inventory);
        }
    }

    @Override
    public boolean existsById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(i) FROM Inventory i WHERE i.productId = :id", Long.class);
        query.setParameter("id", id);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }
}