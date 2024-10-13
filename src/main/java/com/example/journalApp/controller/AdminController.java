package com.example.journalApp.controller;

import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-user")
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userService.getAllUsers();
        if (userList != null && !userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/clear-app-cache")
    public void clearAppCache() {
        appCache.init();

    }
}
