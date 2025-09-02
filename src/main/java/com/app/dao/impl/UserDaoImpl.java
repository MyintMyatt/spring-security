package com.app.dao.impl;

import com.app.dao.UserDao;
import com.app.model.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {


    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public User getUserByEmail(String email) {
//        return sessionFactory
//                .getCurrentSession()
//                .createQuery("from User u WHERE u.userEmail = :email",User.class)
//                .setParameter("email",email)
//                .uniqueResult();
        return (User) sessionFactory.getCurrentSession().get(User.class,email);

    }

    @Override
    public List<User> getAllUser() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    public void saveUser(User user) {
        sessionFactory
                .getCurrentSession()
                .persist(user);
    }

    @Override
    public void updateUser(String email, User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().remove(user);
    }
}
