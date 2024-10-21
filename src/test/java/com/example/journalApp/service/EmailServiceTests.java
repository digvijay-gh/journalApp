package com.example.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("digvijaydsy2@gmail.com","Checking Java Mail Sender","kbfebfebfksldasbjfbeabfkaslbsfkja");
    }
}