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
    private ZSetOperations<String, Long> zSetOps;

    public List<GameResultDto> getTopGames() {
        Set<ZSetOperations.TypedTuple<Long>> results = zSetOps.reverseRangeWithScores(KEY, 0, 2);
        if (results == null || results.isEmpty()) {
            System.out.println("No results found in Redis for key: " + KEY);
        } else {
            System.out.println("DAO Results: " + results);
        }
        return results.stream()
                // TODO изменить null на вызов имени из бд
                .map(item -> new GameResultDto(Objects.requireNonNull(item.getValue()).toString(), null, Objects.requireNonNull(item.getScore()).intValue()))
                .collect(Collectors.toList());
    }


}