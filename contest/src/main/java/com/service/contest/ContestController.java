package com.service.contest;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contests")
public class ContestController {

    private final ContestService service;

    public ContestController(ContestService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contest> getAllContests() {
        return service.getAllContests();
    }
    @PostMapping("/test")
    public Contest createTestContest() {
        Contest c = new Contest();
        c.setTitle("Test Contest");
        c.setUrl("https://test.com");
        c.setStartTime(LocalDateTime.now().plusMinutes(5));
        c.setReminderSent(false);
        return service.saveContest(c);
    }
}