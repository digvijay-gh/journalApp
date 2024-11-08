package com.example.journalApp.dto;

import com.example.journalApp.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;

    @NotNull
    @Schema(description = "User's username")
    private String username;

    @NotNull
    private String password;

    private String email;
    private boolean sentimentAnalysis;
    private List<String> roles;

    // Constructor to convert User entity to UserDTO
    public UserDTO(User user) {
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.sentimentAnalysis = user.isSentimentAnalysis();
        this.roles = user.getRoles();
    }
}
