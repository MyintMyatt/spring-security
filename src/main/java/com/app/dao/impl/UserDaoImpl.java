package com.app.dao.impl;

import com.app.dao.UserDao;
import com.app.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public List<User> getAllUser(int pageNumber, int pageSize,String sortField,String sortOrder) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "from User order by " + sortField + " " + sortOrder;
        Query<User> query = session.createQuery(sql, User.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
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
