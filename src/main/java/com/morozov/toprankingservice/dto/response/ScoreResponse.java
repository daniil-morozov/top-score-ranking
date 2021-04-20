package com.morozov.toprankingservice.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.morozov.toprankingservice.dto.Score;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class ScoreResponse {

  private final UUID id;
  private final String player;
  private final Integer score;
  private final LocalDateTime time;

  public ScoreResponse(Score score) {
    this.id = score.getId();
    this.score = score.getScore();
    this.player = score.getPlayer();
    this.time = score.getTime();
  }

  @JsonGetter
  public UUID getId() {
    return id;
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
    return "Score{" +
        "id=" + id +
        ", player='" + player + '\'' +
        ", score=" + score +
        ", time=" + time +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScoreResponse scoreResponse1 = (ScoreResponse) o;
    return id.equals(scoreResponse1.id) && player.equals(scoreResponse1.player) && score
        .equals(scoreResponse1.score) && time.equals(scoreResponse1.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, player, score, time);
  }
}
