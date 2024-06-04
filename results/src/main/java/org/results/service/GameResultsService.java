package org.results.service;

import org.results.dto.GameResultDto;
import org.results.repo.GameRepo;
import org.results.repo.VoteResultsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameResultsService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private VoteResultsDao voteResultsDao;

    public List<GameResultDto> getTopGames() {
        return voteResultsDao.getTopGames().stream()
                .peek(item -> item.setName(gameRepo.findById(item.getId()).get().getName()))
                .collect(Collectors.toList());
    }
}
