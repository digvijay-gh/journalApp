package com.example.journalApp.controller;

import com.example.journalApp.dto.UserLoginDTO;
import com.example.journalApp.dto.UserSignUpDTO;
import com.example.journalApp.entity.User;
import com.example.journalApp.scheduler.UserScheduler;

import com.example.journalApp.service.UserDetailsServiceImpl;
import com.example.journalApp.service.UserService;
import com.example.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs",description = "Check API,login,signup and check mail service")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserScheduler userScheduler;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Check if API is running")
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @Operation(summary = "User can sign up")
    @PostMapping("/signup")
    public void createUser(@RequestBody UserSignUpDTO newUser) {
        User user =new User(newUser);
        userService.saveNewUser(user);
    }
    @Operation(summary = "User logs in")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO inputUser) {
        User user =new User(inputUser);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Let's irritate everyone by sending mail(uses kafka)")
    @GetMapping("/call-cron")
    public void callScheduler(){
        userScheduler.fetchAllUsersAndSentSAMail();
    }
}
