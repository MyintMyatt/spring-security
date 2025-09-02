package com.app.util.impl;

import com.app.model.dto.UserDto;
import com.app.model.entity.User;
import com.app.util.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapperImpl implements UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto mapUserToDto(User user) {
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User mapDtoToUser(UserDto dto) {
        return modelMapper.map(dto,User.class);
    }
}
