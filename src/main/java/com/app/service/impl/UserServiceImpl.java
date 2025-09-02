package com.app.service.impl;

import com.app.dao.UserDao;
import com.app.dao.impl.RoleDao;
import com.app.model.dto.LoginUserDao;
import com.app.model.dto.UserDto;
import com.app.model.entity.Role;
import com.app.model.entity.User;
import com.app.service.UserService;
import com.app.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public void createUser(UserDto dto) {

        User user = userMapper.mapDtoToUser(dto);

        Set<Role> roles = dto.getRoles().stream()
                .map(role -> roleDao.getRoleByName(role)
                        .orElseGet(() -> roleDao.save(new Role(null,role))))
                        .collect(Collectors.toSet());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userDao.saveUser(user);
    }

    public User updateUser(String email, UserDto dto) {

        User userDetails = userMapper.mapDtoToUser(dto);

        User user = userDao.getUserByEmail(email);
        if (user == null)
            throw new RuntimeException("User Not found exception");
        user.setUserEmail(userDetails.getUserEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setRoles(userDetails.getRoles());
        userDao.saveUser(user);
        return user;
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }


    public String login(LoginUserDao dao) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dao.getUserEmail(), dao.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(getUserByEmail(dao.getUserEmail()));
        }
        return "Validation Failed";

    }

    public void deleteUser(String email) {
        User user = userDao.getUserByEmail(email);
        if (user == null)
            throw new RuntimeException("User Not found exception");
        userDao.deleteUser(user);
    }

    public List<UserDto> getalluser(int page, int size,String sortField,String sortOrder) {
        return userDao.getAllUser(page,size,sortField,sortOrder).stream().map(userMapper::mapUserToDto).collect(Collectors.toList());
    }
}
