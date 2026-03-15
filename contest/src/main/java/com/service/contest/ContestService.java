package com.service.contest;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContestService {

    private final ContestRepository repository;

    public ContestService(ContestRepository repository) {
        this.repository = repository;
    }

    public List<Contest> getAllContests() {
        return repository.findAll();
    }

    public Contest saveContest(Contest contest) {
        return repository.save(contest);
    }
}