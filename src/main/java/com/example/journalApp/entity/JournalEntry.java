package com.example.journalApp.entity;

import com.example.journalApp.dto.JournalEntryDTO;
import com.example.journalApp.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor

public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

    public JournalEntry(JournalEntryDTO journalEntryDTO) {

        this.title = journalEntryDTO.getTitle();
        if (journalEntryDTO.getContent() != null)
            this.content = journalEntryDTO.getContent();
        if (journalEntryDTO.getSentiment() != null)
            this.sentiment = journalEntryDTO.getSentiment();
    }

}
