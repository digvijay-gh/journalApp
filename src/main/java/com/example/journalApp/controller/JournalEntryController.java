package com.example.journalApp.controller;

import com.example.journalApp.dto.JournalEntryDTO;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.JournalEntryResponse;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.JournalEntryService;
import com.example.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "Get all journal,specific journal and update/delete a journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping()
    @Operation(summary = "Get all journal entries of a user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<JournalEntry> entry = user.getJournalEntries();

            List<JournalEntryResponse> responseList = new ArrayList<>();
            for (JournalEntry journalEntry : entry) {
                responseList.add(new JournalEntryResponse(journalEntry));
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "Get specific journal entry of a user")
    @GetMapping("id/{getId}")
    public ResponseEntity<JournalEntry> getJournalByID(@PathVariable String getId) {
        ObjectId id = new ObjectId(getId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> entryList = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!entryList.isEmpty()) {
            Optional<JournalEntry> entry = journalEntryService.getJournalByID(id);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @Operation(summary = "Create a journal entry")
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntryDTO journalEntryDTO) {
        JournalEntry myEntry = new JournalEntry(journalEntryDTO);

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Operation(summary = "Delete a specific journal entry")
    @DeleteMapping("id/{getId}")
    public ResponseEntity<?> deleteJournalByID(@PathVariable String getId) {
        ObjectId id = new ObjectId(getId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean deleted = journalEntryService.deleteJournalByID(id, username);
        if (deleted)
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        else return new ResponseEntity<>("Not deleted", HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Update a journal entry")
    @PutMapping("id/{getId}")
    public ResponseEntity<JournalEntry> updateJournalByID(@PathVariable String getId, @RequestBody JournalEntryDTO journalEntryDTO) {
        ObjectId id = new ObjectId(getId);
        JournalEntry myEntry = new JournalEntry(journalEntryDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        // Check if the user has the specified journal entry
        List<JournalEntry> entryList = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if (!entryList.isEmpty()) {
            Optional<JournalEntry> entry = journalEntryService.getJournalByID(id);
            if (entry.isPresent()) {
                JournalEntry old = entry.get();

                // Update title if provided
                if (myEntry.getTitle() != null && !myEntry.getTitle().isEmpty()) {
                    old.setTitle(myEntry.getTitle());
                }

                // Update content if provided
                if (myEntry.getContent() != null && !myEntry.getContent().isEmpty()) {
                    old.setContent(myEntry.getContent());
                }

                // Update sentiment if provided
                if (myEntry.getSentiment() != null) {
                    old.setSentiment(myEntry.getSentiment());
                }

                // Save the updated entry
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}
