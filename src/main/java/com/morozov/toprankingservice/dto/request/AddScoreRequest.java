package com.morozov.toprankingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AddScoreRequest {
    private final String player;
    private final Integer score;
    private final LocalDateTime time;

    @JsonCreator
    public AddScoreRequest(@JsonProperty("player") String player,
                           @JsonProperty("score") Integer score,
                           @JsonProperty("time") LocalDateTime time) {
        this.player = player;
        this.score = score;
        this.time = time;
    }

    @JsonGetter
    public String getPlayer() {
        return player;
    }

    @JsonGetter
    public Integer getScore() {
        return score;
    }

    @JsonGetter
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
