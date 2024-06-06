package org.results.repo;

import jakarta.annotation.Resource;
import org.results.dto.GameResultDto;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class VoteResultsDao {
    private static final String KEY = "games";

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOps;

    // Метод для проверки наличия ключа 'games'
    public boolean checkIfKeyExists() {
        Boolean exists = zSetOps.getOperations().hasKey(KEY);
        System.out.println("Key '" + KEY + "' exists: " + exists);
        return exists != null && exists;
    }

    public List<GameResultDto> getTopGames() {
        // Логгирование для подтверждения входа в метод
        System.out.println("Entering getTopGames() method.");

        // Проверка наличия ключа 'games'
        checkIfKeyExists();

        // Получение данных из Redis
        Set<ZSetOperations.TypedTuple<String>> results = zSetOps.reverseRangeWithScores(KEY, 0, 2);

        // Дополнительное логгирование
        System.out.println("Results retrieved from Redis: " + results);

        if (results == null || results.isEmpty()) {
            System.out.println("No results found in Redis for key: " + KEY);
        } else {
            System.out.println("DAO Results: " + results);
        }
        return results.stream()
                .map(item -> new GameResultDto(Objects.requireNonNull(item.getValue()).toString(), null, Objects.requireNonNull(item.getScore()).intValue()))
                .collect(Collectors.toList());
    }
}