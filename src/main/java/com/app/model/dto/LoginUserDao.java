package com.app.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDao {
    @Email(message = "please use valid email")
    @NotNull
    private String userEmail;


    @NotEmpty
    @Size(min = 4, message = "password must have 4 characters at least")
    private String password;

}
