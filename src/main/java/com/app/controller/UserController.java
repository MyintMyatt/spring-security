package com.app.controller;

import com.app.model.ApiResponse;
import com.app.model.dto.LoginUserDao;
import com.app.model.dto.UserDto;
import com.app.service.UserService;
import com.google.protobuf.Api;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody  UserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "successfully registered user", null));
    }


    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse<?>> getAllUser() {
        return ResponseEntity.ok(new ApiResponse<>(true,"all users",userService.getalluser()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(new ApiResponse<>(true,"About Me",auth.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginUserDao dao) {
        return ResponseEntity.ok(new ApiResponse<>(true,"JWT token",userService.login(dao)));
    }
}
