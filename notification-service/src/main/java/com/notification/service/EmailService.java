package com.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // ✅ Manual constructor injection
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Contest Tracker 🚀");
        message.setText("You have successfully registered to Contest Tracker.");

        mailSender.send(message);

        System.out.println("Email sent to: " + to);
    }
    public void sendReminderEmail(String to, String contestInfo) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Contest Reminder 🚀");
        message.setText(contestInfo);

        mailSender.send(message);

        System.out.println("Reminder email sent to: " + to);
    }
}