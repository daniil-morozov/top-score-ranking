package com.morozov.toprankingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.morozov.toprankingservice.repository.ScoreRepository;

import java.time.LocalDateTime;

public class PlayerHistoryResponse {
    private final LocalDateTime time;
    private final Integer score;

    @JsonCreator
    public PlayerHistoryResponse(LocalDateTime time, Integer score) {
        this.time = time;
        this.score = score;
    }

    @JsonCreator
    public PlayerHistoryResponse(ScoreRepository.TimeScore timeScore) {
        this.time = timeScore.getTime();
        this.score = timeScore.getScore();
    }

    @JsonGetter
    public LocalDateTime getTime() {
        return time;
    }

    @JsonGetter
    public Integer getScore() {
        return score;
    }
}
