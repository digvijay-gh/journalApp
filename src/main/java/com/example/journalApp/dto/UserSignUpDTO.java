package com.example.journalApp.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
    @NotNull
    @Schema(description = "User's username")
    private String username;
    @NotNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;


}
