package com.parimal.journalApp.scheduler;

import com.parimal.journalApp.entity.JournalEntry;
import com.parimal.journalApp.entity.User;
import com.parimal.journalApp.repository.UserRepoImpl;
import com.parimal.journalApp.service.EmailService;
import com.parimal.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());

            String entry=String.join(" ",sentiments);

            String sentiment=sentimentAnalysisService.getSentiment(entry);

//            emailService.sendMail(user.getEmail(),"Sentiment for Last 7 days", sentiment);
        }
    }


}