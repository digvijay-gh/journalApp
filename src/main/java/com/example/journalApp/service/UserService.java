package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveEntry(User user) {

        userRepository.save(user);
    }
    public boolean saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Duplicate user for {}",user.getUsername());
//            logger.info("Duplicate user");
//            logger.debug("Duplicate user");
//            logger.trace("Duplicate user");
//            logger.warn("Duplicate user");
            return false;
        }

    }
    public void saveNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByID(@PathVariable ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteJournalByID(@PathVariable ObjectId id) {
        userRepository.deleteById(id);
    }
    public boolean deleteByUserName(@PathVariable String username) {
        try{
            userRepository.deleteByUsername(username);
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
