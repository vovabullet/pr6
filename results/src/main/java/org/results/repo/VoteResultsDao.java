package org.results.repo;

import jakarta.annotation.Resource;
import org.results.dto.GameResultDto;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class VoteResultsDao {
    private static final String KEY = "games";

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOps;

    public List<GameResultDto> getTopGames() {
        return zSetOps.reverseRangeWithScores(KEY, 0, 2).stream()
                .map(item -> new GameResultDto(item.getValue(), null, item.getScore().intValue()))
                .collect(Collectors.toList());
    }
}
