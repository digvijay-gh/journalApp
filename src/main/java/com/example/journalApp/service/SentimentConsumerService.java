package com.example.journalApp.service;

import com.example.journalApp.model.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService mailService;


    @KafkaListener(topics = "weekly-sentiments", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(SentimentData sentimentData) {
        log.info("kafka is consuming data for "+sentimentData.getEmail());
        sendEmail(sentimentData);
    }

    private void sendEmail(SentimentData sentimentData) {
        mailService.sendEmail(sentimentData.getEmail(),  "Sentiment for previous week", sentimentData.getSentiment()+ " on Date: "+LocalDateTime.now().toLocalDate()+" at Time "+LocalDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS));
    }
}