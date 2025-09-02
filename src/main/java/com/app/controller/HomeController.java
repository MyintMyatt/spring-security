package com.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/user")
    public String home(){
        return "Welcome user from my website ";
    }

    @GetMapping("/admin")
    public String admin(){
        return "Welcome admin";
    }


    @GetMapping("/getIp")
    public String ip(HttpServletRequest request){
        return request.getRemoteAddr();
    }

}
