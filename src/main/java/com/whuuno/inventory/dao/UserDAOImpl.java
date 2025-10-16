package com.whuuno.inventory.dao;

import com.whuuno.inventory.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(
                "FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(
                "FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        User user = query.uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        Long count = query.uniqueResult();
        return count != null && count > 0;
    }
}
