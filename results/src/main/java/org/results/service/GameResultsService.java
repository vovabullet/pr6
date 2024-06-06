package org.results.service;

import org.results.dto.GameResultDto;
import org.results.model.Game;
import org.results.repo.GameRepo;
import org.results.repo.VoteResultsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameResultsService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private VoteResultsDao voteResultsDao;

    public List<GameResultDto> getTopGames() {
        List<GameResultDto> topGames = voteResultsDao.getTopGames();
        System.out.println("Service Top Games: " + topGames);
        return topGames.stream()
                .peek(item -> {
                    System.out.println("Looking for game with id: " + item.getId());
                    Optional<Game> gameOptional = gameRepo.findById(item.getId());
                    if (gameOptional.isPresent()) {
                        item.setName(gameOptional.get().getName());
                    } else {
                        System.out.println("Game not found for id: " + item.getId());
                        throw new NoSuchElementException("Game not found for id: " + item.getId());
                    }
                })
                .collect(Collectors.toList());
    }
}