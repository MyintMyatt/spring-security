package com.app.model.dto;

import com.app.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;


@Getter
@Setter
public class UserDto {

    @Email(message = "please use valid email")
    @NotNull
    private String userEmail;

    private String userName;

    @NotEmpty
    @Size(min = 4, message = "password must have 4 characters at least")
    private String password;

    private boolean enabled;

    private Set<String> roles;



}
