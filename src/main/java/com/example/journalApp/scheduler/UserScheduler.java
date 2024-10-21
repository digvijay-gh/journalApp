package com.example.journalApp.scheduler;

import com.example.journalApp.cache.AppCache;
import com.example.journalApp.entity.JournalEntry;
import com.example.journalApp.entity.User;
import com.example.journalApp.enums.Sentiment;
import com.example.journalApp.model.SentimentData;
import com.example.journalApp.repository.UserRepository;
import com.example.journalApp.repository.UserRepositoryImpl;
import com.example.journalApp.service.EmailService;
import com.example.journalApp.service.SentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AppCache appCache;
    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 ? * SUN")
//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void fetchAllUsersAndSentSAMail() {
        log.info(LocalDateTime.now().toString());
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(30, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequent = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequent = entry.getKey();
                }
            }
            if (mostFrequent != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequent).build();
                kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                int i=1;
//                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequent.toString());
            }
        }

    }

    @Scheduled(cron = "0 0/10 * 1/1 * ?")
    public void clearAppCache() {
        appCache.init();
    }
}
