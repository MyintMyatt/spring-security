package com.app.service;

import com.app.model.dto.LoginUserDao;
import com.app.model.dto.UserDto;
import com.app.model.entity.User;

import java.util.List;

public interface UserService {

    void createUser(UserDto dto);

    User updateUser(String email, UserDto dto);

    User getUserByEmail(String email);

    String login(LoginUserDao dao);

    void deleteUser(String email);

    List<UserDto> getalluser(int page, int size,String sortField,String sortOrder);
}
