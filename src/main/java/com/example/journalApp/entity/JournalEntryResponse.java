package com.example.journalApp.entity;

import com.example.journalApp.enums.Sentiment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Schema(hidden = true)
@Data
public class JournalEntryResponse {
    private String id; // Change this to String
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

    public JournalEntryResponse(JournalEntry journalEntry) {
        this.id = journalEntry.getId().toHexString(); // Convert ObjectId to hex string
        this.title = journalEntry.getTitle();
        this.content = journalEntry.getContent();
        this.date = journalEntry.getDate();
        this.sentiment = journalEntry.getSentiment();
    }
}
