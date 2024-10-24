package com.example.journalApp.controller;

import com.example.journalApp.cache.AppCache;
import com.example.journalApp.dto.UserLoginDTO;
import com.example.journalApp.dto.UserSignUpDTO;
import com.example.journalApp.entity.User;
import com.example.journalApp.scheduler.UserScheduler;
import com.example.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Get all user, create a ADMIN and clear cache")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserScheduler userScheduler;


    @GetMapping("/all-user")
    @Operation(summary = "Get all the users from db")
    public ResponseEntity<?> getAllUser() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<User> userList = userService.getAllUsers();
            if (userList != null && !userList.isEmpty()) {
                return new ResponseEntity<>(userList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Auth error",HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a admin")
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserSignUpDTO inputUser) {
        User user = new User(inputUser);
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Clear app cache")
    @GetMapping("/clear-app-cache")
    public void clearAppCache() {
        appCache.init();

    }

    @Operation(summary = "Let's irritate everyone by sending mail (needs admin access)")
    @GetMapping("/call-cron")
    public void callScheduler() {
        userScheduler.fetchAllUsersAndSentSAMail();
    }
}
