package com.example.journalApp.dto;

import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.enums.Sentiment;
import com.mongodb.annotations.NotThreadSafe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class JournalEntryDTO {
    @NonNull
    private String title;

    private String content;
    private Sentiment sentiment;


}
