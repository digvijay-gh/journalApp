package com.example.journalApp.service;

import com.example.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService mailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        mailService.sendEmail(sentimentData.getEmail(),  "Sentiment for previous week", sentimentData.getSentiment()+ LocalDateTime.now());
    }
}