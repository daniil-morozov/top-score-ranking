package com.morozov.toprankingservice.repository;

import com.morozov.toprankingservice.entity.ScoreEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends PagingAndSortingRepository<ScoreEntity, UUID> {
    @Query(value = "select s.time as time, s.score as score from score s where s.player = ?1", nativeQuery = true)
    List<TimeScore> findAllByPlayer(String player);

    @Query(value = "select s1.time as time, score from score s1 " +
            "where score=(select max(s2.score) from score s2 where s2.player = s1.player) " +
            "and player= ?1 limit 1", nativeQuery = true)
    TimeScore getTopScore(String player);

    @Query(value = "select s1.time as time, score from score s1 " +
            "where score=(select min(s2.score) from score s2 where s2.player = s1.player) " +
            "and player= ?1 limit 1", nativeQuery = true)
    TimeScore getLowestScore(String player);

    @Query(value = "select avg(score) from score where player = ?1")
    Double getAverageScore(String player);

    interface TimeScore {
        LocalDateTime getTime();
        Integer getScore();
    }
}