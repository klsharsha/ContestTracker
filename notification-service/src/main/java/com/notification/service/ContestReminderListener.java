package com.notification.service;
import java.util.List;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContestReminderListener {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public ContestReminderListener(UserRepository userRepository,
                                    EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "contest-reminder")
    public void listen(String message) {

        List<AppUser> users = userRepository.findAll();

        for (AppUser user : users) {
            emailService.sendReminderEmail(user.getEmail(), message);
        }

        System.out.println("Reminder sent to all users");
    }
}