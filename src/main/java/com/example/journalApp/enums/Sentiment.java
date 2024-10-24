package com.example.journalApp.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents the sentiment of a journal entry.")
public enum Sentiment {
    @Schema(description = "Indicates a happy sentiment.")
    HAPPY,

    @Schema(description = "Indicates a sad sentiment.")
    SAD,

    @Schema(description = "Indicates an angry sentiment.")
    ANGRY,

    @Schema(description = "Indicates an anxious sentiment.")
    ANXIOUS;
}
