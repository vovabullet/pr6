package org.voting.repo;

import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.voting.model.Vote;

@Repository
@Transactional
public class VoteDao {
    private static final String KEY = "games";

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOps;

    public void addNewVote(Vote vote) {
        try {
            System.out.println("Adding vote for game " + vote.getGameId() + " to games list with vote count " + vote.getVoteCount());
            zSetOps.add(KEY, vote.getGameId(), vote.getVoteCount().doubleValue());
            Double score = zSetOps.score(KEY, vote.getGameId());
            System.out.println("Score for game " + vote.getGameId() + " after adding: " + score);
        } catch (Exception e) {
            System.err.println("Error adding vote for game " + vote.getGameId() + ": " + e.getMessage());
        }
    }

    public void incrementVote(String id) {
        try {
            System.out.println("Incrementing vote for game " + id);
            zSetOps.incrementScore(KEY, id, 1);
            Double score = zSetOps.score(KEY, id);
            System.out.println("Score for game " + id + " after incrementing: " + score);
        } catch (Exception e) {
            System.err.println("Error incrementing vote for game " + id + ": " + e.getMessage());
        }
    }
}
