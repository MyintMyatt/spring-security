package com.app.dao;

import com.app.model.entity.User;

import java.util.List;

public interface UserDao {

    User getUserByEmail(String email);

    List<User> getAllUser(int pageNumber, int pageSize,String sortField,String sortOrder);

    void saveUser(User user);

    void updateUser(String email,User user);

    void deleteUser(User user);

}
