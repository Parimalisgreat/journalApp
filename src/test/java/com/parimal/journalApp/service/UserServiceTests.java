package com.parimal.journalApp.service;

import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepo userRepository;

    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUserName("Ram"));
    }


}
