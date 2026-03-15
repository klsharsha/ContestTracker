package com.service.contest;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ContestScheduler {

    private final ContestRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CodeforcesService codeforcesService;

    public ContestScheduler(ContestRepository repository,
                            KafkaTemplate<String, String> kafkaTemplate,
                            CodeforcesService codeforcesService) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.codeforcesService = codeforcesService;
    }

    // Fetch contests every 1 hour
    @Scheduled(fixedRate = 3600000)
    public void fetchContests() {
        codeforcesService.fetchContests();
    }

    // Check every minute for reminders
    @Scheduled(fixedRate = 60000)
    public void sendReminders() {

        List<Contest> contests = repository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Contest contest : contests) {

            if (!contest.isReminderSent() &&
                    contest.getStartTime().isBefore(now.plusMinutes(30)) &&
                    contest.getStartTime().isAfter(now)) {

                kafkaTemplate.send("contest-reminder",
                        "Reminder: " + contest.getTitle());

                contest.setReminderSent(true);
                repository.save(contest);

                System.out.println("Reminder sent for " + contest.getTitle());
            }
        }
    }
}