package com.parimal.journalApp.controller;

import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService service;

    @GetMapping("/ping")
    public String healthCheck(){
        return "ok";
    }


    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        service.saveNewUser(user);
    }
}
