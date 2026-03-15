package com.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class UserRegisteredListener {

    private final UserRepository userRepository;
    private final EmailService es;

    public UserRegisteredListener(UserRepository userRepository,EmailService es) {
        this.userRepository = userRepository;
        this.es=es;
    }

    @KafkaListener(topics = "user-registered")
    public void listen(String email) {

        AppUser user = new AppUser();
        user.setEmail(email);

        userRepository.save(user);
        es.sendEmail(email);
        System.out.println("User stored in notification DB: " + email);
    }
}