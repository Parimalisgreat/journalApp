package com.parimal.journalApp.service;

import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMailTest(){
        emailService.sendMail("parimal.jichkar104@gmail.com","test","Hi?");
    }
}
