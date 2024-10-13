package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry entry, String username) {
        try {
            User user = userService.findByUsername(username);

            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);

        } catch (Exception e) {
            log.error("Exception " + e);
            throw new  RuntimeException("Exception: "+e);
        }

    }

    public void saveEntry(JournalEntry entry) {
        try {
            journalEntryRepository.save(entry);
        } catch (Exception e) {
            log.error("Exception " + e);
        }

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalByID(@PathVariable ObjectId id) {

        return journalEntryRepository.findById(id);

    }

    public boolean deleteJournalByID(@PathVariable ObjectId id, String username) {
        boolean removed=false;
        try{
            User user = userService.findByUsername(username);
              removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry "+e);
        }



    }
}
