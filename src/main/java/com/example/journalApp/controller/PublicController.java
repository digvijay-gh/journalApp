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


}
