package com.parimal.journalApp.controller;

import com.parimal.journalApp.entity.JournalEntry;
import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User oldUser=service.findByUserName(userName);

        if(oldUser!=null){
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            service.saveNewUser(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        service.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
