package com.morozov.toprankingservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Score {

    private final UUID id;
    private final String player;
    private final Integer score;
    private final LocalDateTime time;

    public Score(UUID id, String player, Integer score, LocalDateTime time) {
        this.id = id;
        this.player = player;
        this.score = score;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public Integer getScore() {
        return score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return id.equals(score1.id) && player.equals(score1.player) && score.equals(score1.score) && time.equals(score1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player, score, time);
    }
}
