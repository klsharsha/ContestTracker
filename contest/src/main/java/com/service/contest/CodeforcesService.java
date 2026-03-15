package com.service.contest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CodeforcesService {

    private final ContestRepository repository;

    public CodeforcesService(ContestRepository repository) {
        this.repository = repository;
    }

    public void fetchContests() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://codeforces.com/api/contest.list";

        Map response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> contests =
                (List<Map<String, Object>>) response.get("result");

        for (Map<String, Object> c : contests) {

            if (!"BEFORE".equals(c.get("phase"))) continue;

            Contest contest = new Contest();

            contest.setTitle((String) c.get("name"));
            contest.setUrl("https://codeforces.com/contest/" + c.get("id"));

            Long startSeconds = ((Number) c.get("startTimeSeconds")).longValue();

            contest.setStartTime(
                    LocalDateTime.ofEpochSecond(startSeconds, 0, ZoneOffset.UTC)
            );

            contest.setReminderSent(false);

            repository.save(contest);
        }

        System.out.println("Fetched contests from Codeforces");
    }
}