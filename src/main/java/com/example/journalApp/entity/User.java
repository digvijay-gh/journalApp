package com.example.journalApp.entity;

import com.example.journalApp.dto.UserLoginDTO;
import com.example.journalApp.dto.UserSignUpDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Schema(hidden = true)
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;

    public User(UserSignUpDTO user) {

        this.username = user.getUsername();
        this.password = user.getPassword();
        if (user.getEmail() != null)
            this.email = user.getEmail();
        if (user.isSentimentAnalysis())
            this.sentimentAnalysis = user.isSentimentAnalysis();
    }

    public User(UserLoginDTO user) {

        this.username = user.getUsername();
        this.password = user.getPassword();

    }
}
