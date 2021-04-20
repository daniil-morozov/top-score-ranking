package com.morozov.toprankingservice.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "score")
public class ScoreEntity {

  @Id
  private UUID id;
  private String player;
  private Integer score;
  private LocalDateTime time;

  public ScoreEntity() {

  }

  public ScoreEntity(UUID id, String player, Integer score, LocalDateTime time) {
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
  public String toString() {
    return "Score{" +
        "id=" + id +
        ", player='" + player + '\'' +
        ", score=" + score +
        ", time=" + time +
        '}';
  }
}
