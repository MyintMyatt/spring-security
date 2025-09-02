package com.app.util;

import com.app.model.dto.UserDto;
import com.app.model.entity.User;

public interface UserMapper {

    UserDto mapUserToDto(User user);

    User mapDtoToUser(UserDto dto);

}
