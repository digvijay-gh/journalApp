package com.example.journalApp.controller;

import com.example.journalApp.entity.User;
import com.example.journalApp.scheduler.UserScheduler;
import com.example.journalApp.service.RedisService;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserScheduler userScheduler;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @GetMapping("/redis/get/{str}")
    public String getFromRedis(@PathVariable String str) {
        String s = redisService.get(str, String.class);

        return s;
    }

    @PostMapping("/redis/set/{key}")
    public void getFromRedis(@PathVariable String key, @RequestBody String value) {
        redisService.set(key, value, 100000000000l);

    }
    @GetMapping("/call-cron")
    public void callScheduler(){
        userScheduler.fetchAllUsersAndSentSAMail();
    }
}
