package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

        @ParameterizedTest
    @ArgumentsSource(UserArgsProvider.class)
    public void testFindByUsername(User user){
        assertTrue(userService.saveNewUser(user));

    }
//    @ParameterizedTest
//    @ArgumentsSource(UserArgsUsernameProvider.class)
//    public void testFindByUsername(String username) {
//        assertTrue(userService.deleteByUserName(username));
//
//    }
}
