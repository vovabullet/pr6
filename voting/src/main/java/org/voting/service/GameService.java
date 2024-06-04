package org.voting.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.voting.model.Game;
import org.voting.model.Vote;
import org.voting.repo.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.voting.repo.VoteDao;

import java.util.List;
import java.util.Set;

@Service
public class GameService {
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private VoteDao voteDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<Game> getAll() {
        return gameRepo.findAll();
    }

    public Game addGame(Game game) {
        Game newGame = gameRepo.save(game);
        System.out.println("New game added: " + newGame);
        voteDao.addNewVote(new Vote(newGame.getId(), 0));
        return newGame;
    }

    public void voteById(String id) {
        System.out.println("Voting for game ID: " + id);
        voteDao.incrementVote(id);
    }

    // Временный метод для проверки всех ключей в Redis
    public void checkRedisKeys() {
        Set<String> keys = redisTemplate.keys("*");
        System.out.println("All keys in Redis: " + keys);

        for (String key : keys) {
            System.out.println("Key: " + key + ", Type: " + redisTemplate.type(key));
            if (redisTemplate.type(key) == DataType.ZSET) {
                Set<ZSetOperations.TypedTuple<String>> zSet = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
                for (ZSetOperations.TypedTuple<String> tuple : zSet) {
                    System.out.println("Value: " + tuple.getValue() + ", Score: " + tuple.getScore());
                }
            }
        }
    }
}
