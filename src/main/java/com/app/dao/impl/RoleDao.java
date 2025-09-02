package com.app.dao.impl;

import com.app.model.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Role save(Role role) {
        sessionFactory.getCurrentSession().persist(role);
        return role;
    }

    public Optional<Role> getRoleByName(String name) {
        Role role = sessionFactory.getCurrentSession()
                .createQuery("from Role r where r.name = :name", Role.class)
                .setParameter("name", name).uniqueResult();
        return Optional.ofNullable(role);
    }


}
