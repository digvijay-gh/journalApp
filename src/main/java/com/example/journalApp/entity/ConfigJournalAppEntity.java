package com.example.journalApp.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data
@NoArgsConstructor
@Schema(hidden = true)
public class ConfigJournalAppEntity {

    private String key;
    private String value;



}
