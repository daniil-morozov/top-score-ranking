package com.morozov.toprankingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.time.LocalDateTime;

public class ScoreRequest {
    private final String player;
    private final Integer score;
    private final LocalDateTime time;

    @JsonCreator
    public ScoreRequest(String player, Integer score, LocalDateTime time) {
        this.player = player;
        this.score = score;
        this.time = time;
    }

    @JsonGetter(value = "player")
    public String getPlayer() {
        return player;
    }

    @JsonGetter(value = "score")
    public Integer getScore() {
        return score;
    }

    @JsonGetter(value = "time")
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ScoreRequest{" +
                "player='" + player + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
}
