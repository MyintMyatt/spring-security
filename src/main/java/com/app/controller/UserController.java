package com.app.controller;

import com.app.model.dto.LoginUserDao;
import com.app.model.dto.UserDto;
import com.app.model.response.ApiResponse;
import com.app.model.response.PaginationResponse;
import com.app.service.UserService;
import com.app.service.impl.PaginationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaginationService paginationService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody UserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "successfully registered user", null));
    }


    @GetMapping("/all-users")
    public ResponseEntity<ApiResponse<?>> getAllUser(
            @RequestParam(name = "page",defaultValue = "1", required = false) int page,
            @RequestParam(name = "size",defaultValue = "10", required = false) int size,
            @RequestParam(name = "sortField",defaultValue = "userEmail",required = false) String sortField,
            @RequestParam(name = "sortOrder",defaultValue = "asc",required = false) String sortOrder

    ) {
        int pageSize = paginationService.getDefaultPageSize(size);
        System.out.println("Page Size => " + pageSize);
        return ResponseEntity.ok(new ApiResponse<>(true, "users", new PaginationResponse(userService.getalluser(page, pageSize,sortField,sortOrder), page, pageSize,sortField,sortOrder)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(new ApiResponse<>(true, "About Me", auth.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginUserDao dao) {
        return ResponseEntity.ok(new ApiResponse<>(true, "JWT token", userService.login(dao)));
    }
}
