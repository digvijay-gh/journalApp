package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.scheduler.UserScheduler;
import com.example.journalApp.service.RedisService;
import com.example.journalApp.service.UserDetailsServiceImpl;
import com.example.journalApp.service.UserService;
import com.example.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserScheduler userScheduler;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public void createUser(@RequestBody User user) {

        userService.saveNewUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
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

//    @GetMapping("/redis/get/{str}")
//    public String getFromRedis(@PathVariable String str) {
//        String s = redisService.get(str, String.class);
//
//        return s;
//    }
//
//    @PostMapping("/redis/set/{key}")
//    public void getFromRedis(@PathVariable String key, @RequestBody String value) {
//        redisService.set(key, value, 100000000000l);
//
//    }
    @GetMapping("/call-cron")
    public void callScheduler(){
        userScheduler.fetchAllUsersAndSentSAMail();
    }
}
