package com.example.journalApp.controller;

import com.example.journalApp.api.response.WeatherResponse;
import com.example.journalApp.dto.UserDTO;
import com.example.journalApp.dto.UserLoginDTO;
import com.example.journalApp.dto.UserSignUpDTO;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.service.UserService;
import com.example.journalApp.service.WeatherService;
import com.example.journalApp.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User APIs", description = "Update,delete and greet user")
public class UserController {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "User can sign up")
    @PostMapping("/signup")
    public void createUser(@RequestBody UserSignUpDTO newUser) {
        User user = new User(newUser);
        userService.saveNewUser(user);
    }

    @Operation(summary = "User logs in")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO inputUser) {
        User user = new User(inputUser);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "Get user details")
    @GetMapping
    public ResponseEntity<?> getUserDetails(){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(name);
            return new ResponseEntity<>(new UserDTO(user),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


    }

    @Operation(summary = "Update a User")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserSignUpDTO userSignUpDTO) {

        try {
            User user = new User(userSignUpDTO);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userInDb = userService.findByUsername(username);
            if (userInDb != null) {
                userInDb.setUsername(user.getUsername());
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
                if (user.getEmail() != null) userInDb.setEmail(user.getEmail());
                userInDb.setSentimentAnalysis(user.isSentimentAnalysis());
                userService.saveEntry(userInDb);

            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Delete a User")
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            userRepository.deleteByUsername(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Greet the User")
    @GetMapping("/{city}")
    public ResponseEntity<?> greeting(@PathVariable String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = "";
        WeatherResponse weatherResponse = weatherService.getWeather(city);
        if (weatherResponse != null) {
            greeting = ", Weather feels like: " + weatherResponse.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }


}
